"use client"

import type React from "react"
import { useState, useEffect } from "react"
import { Calendar, Clock, User, Phone, Mail, X } from "lucide-react"

interface FormErrors {
  firstName?: string
  lastName?: string
  email?: string
  mobile?: string
  service?: string
  preferredDate?: string
  preferredTime?: string
  notes?: string
}

// Services list - Easy to update and maintain
const DENTAL_SERVICES = [
  "Dental Consultation",
  "Oral Prophylaxis",
  "Fillings",
  "Tooth Extraction",
  "Odontectomy",
  "Root Canal",
  "Dental Braces",
  "Teeth Whitening",
  "Dentures",
  "Implant Dentistry",
  "Gum Treatment",
  "Oral Surgery",
  "TMD Problems"
]

export default function AppointmentForm() {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    mobile: "", // Changed from 'phone' to 'mobile' to match DTO
    preferredDate: "",
    preferredTime: "",
    service: DENTAL_SERVICES[0], // Default to first service
    notes: "", // Changed from 'message' to 'notes' to match DTO (max 150 chars)
    privacyAgreed: false,
  })

  const [errors, setErrors] = useState<FormErrors>({})
  const [submitting, setSubmitting] = useState(false)
  const [showSuccessModal, setShowSuccessModal] = useState(false)
  const [recaptchaReady, setRecaptchaReady] = useState(false)

  const getTodayDate = () => {
    const today = new Date()
    return today.toISOString().split("T")[0]
  }

  const getMaxBookingDate = () => {
    const today = new Date()
    const currentMonth = today.getMonth() // 0-based (December = 11)
    
    // If it's December, allow booking until January 31st of next year
    if (currentMonth === 11) {
      const nextYear = today.getFullYear() + 1
      const lastDayOfJanuary = new Date(nextYear, 1, 0) // Last day of January next year
      return lastDayOfJanuary.toISOString().split("T")[0]
    } else {
      // Otherwise, allow booking until December 31st of current year
      const lastDayOfYear = new Date(today.getFullYear(), 11, 31)
      return lastDayOfYear.toISOString().split("T")[0]
    }
  }

  useEffect(() => {
    const script = document.createElement("script")
    script.src = "https://www.google.com/recaptcha/api.js"
    script.async = true
    script.defer = true
    script.onload = () => {
      setRecaptchaReady(true)
    }
    document.head.appendChild(script)
  }, [])

  const validateField = (name: string, value: string): string | undefined => {
    switch (name) {
      case "firstName":
      case "lastName":
        if (!value.trim()) {
          return name === "firstName" ? "First name is required" : "Last name is required"
        }
        break
      case "email":
        if (!value.trim()) {
          return "Email is required"
        }
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
        if (!emailRegex.test(value)) {
          return "Invalid email"
        }
        break
      case "mobile":
        if (!value.trim()) {
          return "Mobile number is required"
        }
        const mobileRegex = /^09\d{9}$/
        if (!mobileRegex.test(value.replace(/\D/g, ""))) {
          return "Invalid mobile number. Use format: 09XXXXXXXXX"
        }
        break
      case "service":
        if (!value.trim()) {
          return "Service is required"
        }
        break
      case "preferredDate":
        if (!value.trim()) {
          return "Preferred date is required"
        }
        const selectedDate = new Date(value)
        const today = new Date()
        today.setHours(0, 0, 0, 0)

        if (selectedDate < today) {
          return "Cannot book appointments for past dates"
        }

        // Special case: If current month is December, allow booking for January of next year
        if (today.getMonth() === 11 && // December (0-based)
            selectedDate.getFullYear() === today.getFullYear() + 1 &&
            selectedDate.getMonth() === 0) { // January (0-based)
          // Valid: December allows January next year booking
          break
        }
        
        // Regular validation: appointments within current year only
        const lastDayOfYear = new Date(today.getFullYear(), 11, 31) // December 31st current year
        if (selectedDate > lastDayOfYear) {
          if (today.getMonth() === 11) { // December
            return `During December, appointments can be booked through January ${today.getFullYear() + 1}`
          } else {
            return `Appointments can only be booked within ${today.getFullYear()}`
          }
        }
        break
      case "preferredTime":
        if (!value.trim()) {
          return "Preferred time is required"
        }
        break
      case "notes":
        // Notes field is optional, only validate if not empty
        if (value && value.length > 150) {
          return "Notes cannot exceed 150 characters"
        }
        break
    }
    return undefined
  }

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    const { name, value, type } = e.target

    if (type === "checkbox") {
      setFormData((prev) => ({ ...prev, [name]: (e.target as HTMLInputElement).checked }))
    } else {
      let finalValue = value
      if (name === "mobile") {
        finalValue = value.replace(/\D/g, "").slice(0, 11) // Only digits, max 11 chars
      }

      setFormData((prev) => ({ ...prev, [name]: finalValue }))

      const error = validateField(name, finalValue)
      setErrors((prev) => ({
        ...prev,
        [name]: error,
      }))
    }
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()

    //Validate frontend fields
    const newErrors: FormErrors = {}
    Object.keys(formData).forEach((key) => {
      if (key !== "privacyAgreed") {
        const error = validateField(key, formData[key as keyof typeof formData] as string)
        if (error) {
          newErrors[key as keyof FormErrors] = error
        }
      }
    })

    //If validation errors exist — stop
    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors)
      return
    }

    if (!formData.privacyAgreed) {
      alert("Please agree to the data privacy policy to continue.")
      return
    }

    if (!recaptchaReady) {
      alert("reCAPTCHA is still loading. Please try again.")
      return
    }

    //Get reCAPTCHA token
    const captchaToken = (window as any).grecaptcha.getResponse()
    if (!captchaToken) {
      alert("Please complete the captcha.")
      return
    }

    //Build final payload expected by Spring Boot backend
    const payload = {
      ...formData,
      captchaToken: captchaToken,
      // Send null for empty notes instead of empty string to avoid backend validation
      notes: formData.notes.trim() || null
    }

    console.info("Submitting appointment with payload:", payload)

    try {
      setSubmitting(true)

      const res = await fetch("https://san-rafael-dental-clinic.onrender.com/api/v1/appointments", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      })

      if (!res.ok) {
        const errorData = await res.json()
        alert("Submission failed: " + errorData.message)
        return
      }

      //Show success modal
      setShowSuccessModal(true)

      //Reset form
      setFormData({
        firstName: "",
        lastName: "",
        email: "",
        mobile: "",
        service: DENTAL_SERVICES[0],
        preferredDate: "",
        preferredTime: "",
        notes: "",
        privacyAgreed: false,
      })

      setErrors({});
      (window as any).grecaptcha.reset()
    } catch (err) {
      console.error("Detailed error:", err)
      alert("Network or server error. Please check console for details and ensure CORS is configured on your backend.")
    } finally {
      setSubmitting(false)
    }
  }

  const handleCloseModal = () => {
    setShowSuccessModal(false)
  }

  return (
    <>
      <section id="appointment" className="py-20 md:py-32 bg-primary/5">
        <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
          {/* Header */}
          <div className="text-center mb-12">
            <h2 className="text-3xl md:text-5xl font-bold text-foreground mb-4">Book Your Appointment - Mandaluyong's Best Dentist</h2>
            <p className="text-lg text-muted-foreground">Schedule a visit with Dr. Ronald Remulla and our expert team</p>
          </div>

          {/* Form */}
          <form onSubmit={handleSubmit} className="bg-background rounded-2xl shadow-lg p-8 border border-border">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
              <div>
                <label className="block text-sm font-semibold text-foreground mb-2">First Name</label>
                <div className="relative">
                  <User className="absolute left-3 top-3 w-5 h-5 text-muted-foreground" />
                  <input
                    type="text"
                    name="firstName"
                    value={formData.firstName}
                    onChange={handleChange}
                    placeholder="John"
                    className={`w-full pl-10 pr-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary bg-input text-foreground placeholder-muted-foreground ${
                      errors.firstName ? "border-red-500" : "border-border"
                    }`}
                  />
                </div>
                {errors.firstName && <p className="text-red-500 text-xs mt-1">{errors.firstName}</p>}
              </div>

              <div>
                <label className="block text-sm font-semibold text-foreground mb-2">Last Name</label>
                <div className="relative">
                  <User className="absolute left-3 top-3 w-5 h-5 text-muted-foreground" />
                  <input
                    type="text"
                    name="lastName"
                    value={formData.lastName}
                    onChange={handleChange}
                    placeholder="Doe"
                    className={`w-full pl-10 pr-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary bg-input text-foreground placeholder-muted-foreground ${
                      errors.lastName ? "border-red-500" : "border-border"
                    }`}
                  />
                </div>
                {errors.lastName && <p className="text-red-500 text-xs mt-1">{errors.lastName}</p>}
              </div>

              {/* Email */}
              <div>
                <label className="block text-sm font-semibold text-foreground mb-2">Email</label>
                <div className="relative">
                  <Mail className="absolute left-3 top-3 w-5 h-5 text-muted-foreground" />
                  <input
                    type="email"
                    name="email"
                    value={formData.email}
                    onChange={handleChange}
                    placeholder="john@example.com"
                    className={`w-full pl-10 pr-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary bg-input text-foreground placeholder-muted-foreground ${
                      errors.email ? "border-red-500" : "border-border"
                    }`}
                  />
                </div>
                {errors.email && <p className="text-red-500 text-xs mt-1">{errors.email}</p>}
              </div>

              {/* Mobile */}
              <div>
                <label className="block text-sm font-semibold text-foreground mb-2">Mobile Number</label>
                <div className="relative">
                  <Phone className="absolute left-3 top-3 w-5 h-5 text-muted-foreground" />
                  <input
                    type="tel"
                    name="mobile"
                    value={formData.mobile}
                    onChange={handleChange}
                    placeholder="09XXXXXXXXX"
                    maxLength={11}
                    className={`w-full pl-10 pr-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary bg-input text-foreground placeholder-muted-foreground ${
                      errors.mobile ? "border-red-500" : "border-border"
                    }`}
                  />
                </div>
                <p className="text-xs text-muted-foreground mt-1">Format: 09XXXXXXXXX (11 digits)</p>
                {errors.mobile && <p className="text-red-500 text-xs mt-1">{errors.mobile}</p>}
              </div>

              {/* Service */}
              <div>
                <label className="block text-sm font-semibold text-foreground mb-2">Service</label>
                <select
                  name="service"
                  value={formData.service}
                  onChange={handleChange}
                  className={`w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary bg-input text-foreground ${
                    errors.service ? "border-red-500" : "border-border"
                  }`}
                >
                  {DENTAL_SERVICES.map((service) => (
                    <option key={service} value={service}>
                      {service}
                    </option>
                  ))}

                </select>
                {errors.service && <p className="text-red-500 text-xs mt-1">{errors.service}</p>}
              </div>

              {/* Date */}
              <div>
                <label className="block text-sm font-semibold text-foreground mb-2">Preferred Date</label>
                <div className="relative">
                  <Calendar className="absolute left-3 top-3 w-5 h-5 text-muted-foreground" />
                  <input
                    type="date"
                    name="preferredDate"
                    value={formData.preferredDate}
                    onChange={handleChange}
                    min={getTodayDate()}
                    max={getMaxBookingDate()}
                    className={`w-full pl-10 pr-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary bg-input text-foreground ${
                      errors.preferredDate ? "border-red-500" : "border-border"
                    }`}
                  />
                </div>
                {errors.preferredDate && <p className="text-red-500 text-xs mt-1">{errors.preferredDate}</p>}
              </div>

              {/* Time */}
              <div>
                <label className="block text-sm font-semibold text-foreground mb-2">Preferred Time</label>
                <div className="relative">
                  <Clock className="absolute left-3 top-3 w-5 h-5 text-muted-foreground" />
                  <input
                    type="time"
                    name="preferredTime"
                    value={formData.preferredTime}
                    onChange={handleChange}
                    className={`w-full pl-10 pr-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary bg-input text-foreground ${
                      errors.preferredTime ? "border-red-500" : "border-border"
                    }`}
                  />
                </div>
                {errors.preferredTime && <p className="text-red-500 text-xs mt-1">{errors.preferredTime}</p>}
              </div>
            </div>

            {/* Notes */}
            <div className="mb-6">
              <div className="flex justify-between items-center mb-2">
                <label className="block text-sm font-semibold text-foreground">Additional Notes</label>
                <span
                  className={`text-xs ${formData.notes.length > 120 ? "text-orange-500" : "text-muted-foreground"}`}
                >
                  {formData.notes.length}/150
                </span>
              </div>
              <textarea
                name="notes"
                value={formData.notes}
                onChange={handleChange}
                placeholder="Any additional information or concerns..."
                maxLength={150}
                rows={4}
                className={`w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary bg-input text-foreground placeholder-muted-foreground resize-none ${
                  errors.notes ? "border-red-500" : "border-border"
                }`}
              />
              {errors.notes && <p className="text-red-500 text-xs mt-1">{errors.notes}</p>}
            </div>

            <div className="mb-6 flex justify-center">
              <div className="g-recaptcha" data-sitekey="6LehmhYsAAAAAKqNdycuxw1KJsikBbLG-SyXZyAI"></div>
            </div>

            <div className="mb-6 flex items-start gap-3">
              <input
                type="checkbox"
                name="privacyAgreed"
                id="privacy"
                checked={formData.privacyAgreed}
                onChange={handleChange}
                className="mt-1 w-5 h-5 rounded border-border focus:ring-2 focus:ring-primary cursor-pointer"
              />
              <label htmlFor="privacy" className="text-sm text-muted-foreground cursor-pointer">
                I agree to the collection and processing of my personal data by San Rafael Dental Clinic for business
                purposes only, including appointment scheduling and dental care provision. Your data will be handled in
                accordance with our privacy policy.
              </label>
            </div>

            {/* Submit Button */}
            <button
              type="submit"
              className="w-full bg-primary text-primary-foreground py-3 rounded-lg font-semibold hover:bg-primary/90 transition-colors disabled:opacity-50"
              disabled={!formData.privacyAgreed || submitting}
            >
              {submitting ? "Submitting..." : "Book Appointment"}
            </button>
          </form>

          {/* Operating Hours */}
          <div className="mt-12 grid grid-cols-1 md:grid-cols-3 gap-6">
            <div className="bg-card border border-border p-6 rounded-lg text-center">
              <h3 className="font-semibold text-foreground mb-2">Hours</h3>
              <p className="text-sm text-muted-foreground">Mon-Fri: 8:00 AM - 6:00 PM</p>
              <p className="text-sm text-muted-foreground">Sat: 9:00 AM - 2:00 PM</p>
              <p className="text-sm text-muted-foreground">Sun: Closed</p>
            </div>
            <div className="bg-card border border-border p-6 rounded-lg text-center">
              <h3 className="font-semibold text-foreground mb-2">Phone</h3>
              <a href="tel:+14155551234" className="text-primary font-semibold hover:text-primary/80">
                +63 917 801 4040
              </a>
            </div>
            <div className="bg-card border border-border p-6 rounded-lg text-center">
              <h3 className="font-semibold text-foreground mb-2">Location</h3>
              <p className="text-sm text-muted-foreground">
                93B San Rafael Street, Plainview
                <br />
                Mandaluyong City, Metro Manila 1550
              </p>
            </div>
          </div>
        </div>
      </section>

      {showSuccessModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50 p-4">
          <div className="bg-background rounded-2xl shadow-2xl max-w-md w-full p-8 border border-border">
            {/* Close Button */}
            <button
              onClick={handleCloseModal}
              className="absolute top-4 right-4 p-1 hover:bg-accent/10 rounded-lg transition-colors"
            >
              <X className="w-5 h-5 text-muted-foreground" />
            </button>

            {/* Success Icon */}
            <div className="flex justify-center mb-6">
              <div className="w-16 h-16 bg-accent/10 rounded-full flex items-center justify-center">
                <svg className="w-8 h-8 text-accent" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                </svg>
              </div>
            </div>

            {/* Title */}
            <h3 className="text-2xl font-bold text-foreground text-center mb-4">Appointment Request Submitted</h3>

            {/* Message */}
            <div className="space-y-4 mb-8">
              <p className="text-muted-foreground text-center">
                Thank you for scheduling an appointment with San Rafael Dental Clinic in Mandaluyong City!
              </p>

              <div className="bg-primary/5 border border-primary/20 rounded-lg p-4 space-y-3">
                <div className="flex gap-3">
                  <div className="flex-shrink-0">
                    <span className="inline-flex items-center justify-center h-6 w-6 rounded-full bg-primary/20 text-primary text-sm font-semibold">
                      1
                    </span>
                  </div>
                  <p className="text-sm text-foreground">
                    <span className="font-semibold">Review & Confirmation:</span> Our team will review your appointment
                    request
                  </p>
                </div>

                <div className="flex gap-3">
                  <div className="flex-shrink-0">
                    <span className="inline-flex items-center justify-center h-6 w-6 rounded-full bg-primary/20 text-primary text-sm font-semibold">
                      2
                    </span>
                  </div>
                  <p className="text-sm text-foreground">
                    <span className="font-semibold">Availability Check:</span> We will verify slot availability for your
                    preferred date and time
                  </p>
                </div>

                <div className="flex gap-3">
                  <div className="flex-shrink-0">
                    <span className="inline-flex items-center justify-center h-6 w-6 rounded-full bg-primary/20 text-primary text-sm font-semibold">
                      3
                    </span>
                  </div>
                  <p className="text-sm text-foreground">
                    <span className="font-semibold">Follow-up Contact:</span> We will reach out to you via email or
                    phone to confirm or suggest alternative slots
                  </p>
                </div>
              </div>

              <p className="text-xs text-muted-foreground text-center italic">
                Please expect to hear from us within 24 business hours.
              </p>
            </div>

            {/* Close Button */}
            <button
              onClick={handleCloseModal}
              className="w-full bg-primary text-primary-foreground py-3 rounded-lg font-semibold hover:bg-primary/90 transition-colors"
            >
              Done
            </button>
          </div>
        </div>
      )}
    </>
  )
}
