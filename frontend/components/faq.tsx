"use client"

import { useState } from "react"
import { ChevronDown } from "lucide-react"

interface FAQItem {
  id: string
  question: string
  answer: string
}

const faqItems: FAQItem[] = [
  {
    id: "faq-1",
    question: "What should I do before my first appointment at San Rafael Dental Mandaluyong?",
    answer:
      "Before your first appointment at our Plainview, Mandaluyong clinic, please arrive 10-15 minutes early to complete any necessary paperwork. If possible, bring your dental insurance card and a valid ID. This helps us provide you with the best care and ensure smooth check-in at our San Rafael Dental facility.",
  },
  {
    id: "faq-2",
    question: "Do you offer payment plans or financing options in Mandaluyong?",
    answer:
      "Yes, we understand that dental care can be an investment. At San Rafael Dental Clinic in Mandaluyong City, we offer flexible payment plans and work with various financing options to make quality dental care accessible to families in Plainview, Shaw Boulevard, and Metro Manila. Please speak with our staff about available options during your visit.",
  },
  {
    id: "faq-3",
    question: "How long does a typical dental appointment take at your Mandaluyong clinic?",
    answer:
      "Most appointments at San Rafael Dental Clinic in Mandaluyong take between 30-60 minutes depending on the service. Initial consultations may take longer as Dr. Ronald Remulla needs to review your dental history and conduct a thorough examination. Emergency appointments are prioritized and scheduled accordingly for patients in Plainview and surrounding areas.",
  },
  {
    id: "faq-4",
    question: "Is San Rafael Dental Clinic Mandaluyong accepting new patients?",
    answer:
      "Yes, we are actively welcoming new patients from Mandaluyong City, Plainview, Shaw Boulevard, and all of Metro Manila! Dr. Ronald Remulla and our team believe in providing personalized care to all our patients. If you're new to our clinic, we recommend scheduling a comprehensive examination to establish your dental baseline.",
  },
  {
    id: "faq-5",
    question: "What payment options do you accept at your Mandaluyong clinic?",
    answer:
      "We accept various payment methods for your convenience at San Rafael Dental Clinic in Mandaluyong. We accept cash, credit cards, GCash, and BPI bank transfers via QR code. This makes it easy for patients from Plainview, Shaw Boulevard, and Metro Manila to pay for their dental treatments in the most convenient way possible.",
  },
  {
    id: "faq-6",
    question: "Do you accept HMO or health insurance coverage?",
    answer:
      "We are currently planning to integrate HMO payment options at San Rafael Dental Clinic Mandaluyong to make dental care more accessible to our patients. While we work on establishing partnerships with major HMO providers, we encourage you to contact our clinic directly to discuss your specific insurance coverage and potential reimbursement options for your dental treatments.",
  },
  {
    id: "faq-7",
    question: "Is parking available at your Plainview, Mandaluyong location?",
    answer:
      "Yes, we have parking spaces available for our patients at San Rafael Dental Clinic located at 93B San Rafael Street, Plainview, Mandaluyong. This makes it convenient for families visiting our clinic from Shaw Boulevard, Metro Manila, and surrounding areas to park safely while receiving dental care.",
  },
]

export default function FAQ() {
  const [expandedId, setExpandedId] = useState<string | null>(null)

  const toggleFAQ = (id: string) => {
    setExpandedId(expandedId === id ? null : id)
  }

  return (
    <section id="faq" className="py-20 md:py-32 bg-background shadow-xl">
      <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="text-center mb-12">
          <h2 className="text-3xl md:text-5xl font-bold text-foreground mb-4">FAQ - Your Mandaluyong Dental Questions Answered</h2>
          <p className="text-lg text-muted-foreground">Find answers to common questions about our dental services in Plainview, Mandaluyong City</p>
        </div>

        {/* FAQ Accordion */}
        <div className="space-y-4">
          {faqItems.map((item) => (
            <div
              key={item.id}
              className="border border-border rounded-lg overflow-hidden hover:border-primary/50 transition-colors"
            >
              <button
                onClick={() => toggleFAQ(item.id)}
                className="w-full flex items-center justify-between p-6 bg-card hover:bg-card/80 transition-colors text-left"
              >
                <span className="text-lg font-semibold text-foreground pr-4">{item.question}</span>
                <ChevronDown
                  className={`w-5 h-5 text-primary flex-shrink-0 transition-transform duration-300 ${
                    expandedId === item.id ? "transform rotate-180" : ""
                  }`}
                />
              </button>

              {/* Answer - Expandable Section */}
              {expandedId === item.id && (
                <div className="px-6 py-4 bg-background border-t border-border">
                  <p className="text-muted-foreground leading-relaxed">{item.answer}</p>
                </div>
              )}
            </div>
          ))}
        </div>
      </div>
    </section>
  )
}
