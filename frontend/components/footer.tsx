import { Phone, Mail, MapPin, Facebook, Twitter, Instagram } from "lucide-react"

export default function Footer() {
  const currentYear = new Date().getFullYear()

  return (
    <footer id="contact" className="bg-primary text-primary-foreground">
      <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Main Footer Content */}
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8 py-12">
          {/* Clinic Info */}
          <div>
            <div className="flex items-center gap-2 mb-4">

              <h3 className="font-bold">San Rafael Dental Clinic</h3>
            </div>
            <p className="text-sm opacity-90">
              Providing exceptional dental care to Mandaluyong City, Plainview, Shaw Boulevard, and Metro Manila families since 1999. Your trusted neighborhood dentist.
            </p>
          </div>

          {/* Quick Links */}
          <div>
            <h4 className="font-semibold mb-4">Quick Links</h4>
            <ul className="space-y-2 text-sm">
              <li>
                <a href="#services" className="opacity-90 hover:opacity-100 transition-opacity">
                  Services
                </a>
              </li>
              <li>
                <a href="#appointment" className="opacity-90 hover:opacity-100 transition-opacity">
                  Book Appointment
                </a>
              </li>
              <li>
                <a href="#gallery" className="opacity-90 hover:opacity-100 transition-opacity">
                  Gallery
                </a>
              </li>
              <li>
                <a href="#contact" className="opacity-90 hover:opacity-100 transition-opacity">
                  Contact
                </a>
              </li>
            </ul>
          </div>

          {/* Contact Info */}
          <div>
            <h4 className="font-semibold mb-4">Contact</h4>
            <ul className="space-y-3 text-sm">
              <li className="flex items-center gap-2">
                <Phone className="w-4 h-4" />
                <a href="tel:+14155551234" className="opacity-90 hover:opacity-100 transition-opacity">
                  +63 917 801 4040
                </a>
              </li>
              <li className="flex items-center gap-2">
                <Mail className="w-4 h-4" />
                <a href="mailto:hello@sanrafaeldental.com" className="opacity-90 hover:opacity-100 transition-opacity">
                  hello@srdentalcare.com
                </a>
              </li>
              <li className="flex items-start gap-2">
                <MapPin className="w-4 h-4 mt-0.5 flex-shrink-0" />
                <span className="opacity-90">
                  93B San Rafael
                  <br />
                  Plainview, Mandaluyong 1550
                </span>
              </li>
            </ul>
          </div>

          {/* Hours */}
          <div>
            <h4 className="font-semibold mb-4">Hours</h4>
            <ul className="space-y-2 text-sm opacity-90">
              <li>
                Monday - Friday
                <br />
                <span className="font-medium">8:00 AM - 6:00 PM</span>
              </li>
              <li>
                Saturday
                <br />
                <span className="font-medium">9:00 AM - 2:00 PM</span>
              </li>
              <li>
                Sunday
                <br />
                <span className="font-medium">Closed</span>
              </li>
            </ul>
          </div>
        </div>

        {/* Divider */}
        <div className="border-t border-primary-foreground/20"></div>

        {/* Bottom Footer */}
        <div className="flex flex-col md:flex-row justify-between items-center py-8 gap-4">
          <p className="text-sm opacity-90">Copyright {currentYear} San Rafael Dental Clinic Mandaluyong. Serving Plainview, Shaw Boulevard & Metro Manila. All rights reserved.</p>
          <div className="flex items-center gap-4">
            <a href="https://www.facebook.com/profile.php?id=100084249220187" className="opacity-90 hover:opacity-100 transition-opacity" aria-label="Facebook">
              <Facebook className="w-5 h-5" />
            </a>
          </div>
        </div>
      </div>
    </footer>
  )
}
