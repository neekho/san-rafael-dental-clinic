import { Star } from "lucide-react"

const testimonials = [
  {
    name: "Hayde Cestina",
    role: "Mandaluyong Patient",
    text: "Dr. Ronald Remulla and the team at San Rafael Dental Center made me feel so comfortable. The teeth whitening treatment in their Plainview clinic made a huge difference!",
    rating: 5,
    avatar: "/placeholder-user.jpg",
  },
  {
    name: "Rachelle Ambrosio",
    role: "Shaw Boulevard Resident",
    text: "Excellent dental care in Metro Manila. Professional, friendly staff at San Rafael Dental Center in Mandaluyong, and they took time to explain everything.",
    rating: 5,
    avatar: "/placeholder-user.jpg",
  },
  {
    name: "Aubrey Lucero",
    role: "Plainview Resident",
    text: "My dental implant at San Rafael Dental Center looks and feels completely natural. Quality dental care in Mandaluyong City. I couldn't be happier with the results!",
    rating: 5,
    avatar: "/placeholder-user.jpg",
  },
  {
    name: "Marissa Watanabe",
    role: "Metro Manila Patient",
    text: "No more dental anxiety! Dr. Ronald Remulla and the team's care and attention to detail at their Mandaluyong clinic is truly professional.",
    rating: 5,
    avatar: "/placeholder-user.jpg",
  },
]

export default function Testimonials() {
  return (
    <section id="testimonials" className="py-20 md:py-32 bg-background">
      <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="text-center mb-16">
          <h2 className="text-3xl md:text-5xl font-bold text-foreground mb-4">Patient Reviews - Your Mandaluyong Dental Center</h2>
          <p className="text-lg text-muted-foreground">
            Don't just take our word for it - hear from our satisfied patients in Plainview, Shaw Boulevard, and Metro Manila
          </p>
        </div>

        {/* Testimonials Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          {testimonials.map((testimonial, index) => (
            <div
              key={index}
              className="bg-card border border-border rounded-xl p-6 hover:shadow-lg transition-all flex flex-col justify-between"
            >
              {/* Rating */}
              <div className="flex gap-1 mb-4">
                {[...Array(testimonial.rating)].map((_, i) => (
                  <Star key={i} className="w-4 h-4 fill-accent text-accent" />
                ))}
              </div>

              {/* Testimonial Text */}
              <p className="text-muted-foreground text-sm mb-6 line-clamp-4">"{testimonial.text}"</p>

              {/* Author */}
              <div className="flex items-center gap-3">
                <img
                  src={testimonial.avatar || "/placeholder.svg"}
                  alt={testimonial.name}
                  className="w-10 h-10 rounded-full"
                />
                <div>
                  <div className="font-semibold text-foreground text-sm">{testimonial.name}</div>
                  <div className="text-xs text-muted-foreground">{testimonial.role}</div>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  )
}
