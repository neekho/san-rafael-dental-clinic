import { Smile, Sparkles, Shield, Zap } from "lucide-react"

const services = [
  {
    icon: Smile,
    title: "Professional Cleanings & Oral Prophylaxis",
    description: "Regular dental cleanings in Mandaluyong to maintain optimal oral health and prevent dental issues for the whole family.",
  },
  {
    icon: Sparkles,
    title: "Cosmetic Dentistry & Smile Makeovers",
    description: "Professional teeth whitening, veneers, and bonding services in Mandaluyong City to enhance your smile's appearance and boost confidence.",
  },
  {
    icon: Shield,
    title: "Restorative & Emergency Dental Care",
    description: "Expert fillings, crowns, root canals, and emergency dental services in Plainview, Mandaluyong to restore damaged or diseased teeth.",
  },
  {
    icon: Zap,
    title: "Dental Implants & Tooth Replacement",
    description: "Advanced dental implant procedures in Mandaluyong City - permanent tooth replacement solutions for a natural-looking, confident smile that lasts.",
  },
]

export default function Services() {
  return (
    <section id="services" className="py-20 md:py-32 bg-background shadow-xl">
      <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="text-center mb-16">
          <h2 className="text-3xl md:text-5xl font-bold text-foreground mb-4">Dental Services in Mandaluyong City</h2>
          <p className="text-lg text-muted-foreground max-w-2xl mx-auto">
            Comprehensive dental care for families in Plainview, Shaw Boulevard, and Metro Manila. Professional treatments with modern technology and compassionate care since 1999.
          </p>
        </div>

        {/* Services Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          {services.map((service, index) => {
            const Icon = service.icon
            return (
              <div
                key={index}
                className="p-6 bg-card border border-border rounded-xl hover:shadow-lg transition-all duration-300 hover:border-primary"
              >
                <div className="w-12 h-12 bg-primary/10 rounded-lg flex items-center justify-center mb-4">
                  <Icon className="w-6 h-6 text-primary" />
                </div>
                <h3 className="text-lg font-semibold text-foreground mb-2">{service.title}</h3>
                <p className="text-sm text-muted-foreground">{service.description}</p>
              </div>
            )
          })}
        </div>
      </div>
    </section>
  )
}
