export default function Hero() {
  return (
    <section className="relative w-full h-screen md:h-[600px] overflow-hidden">
      <div
        className="absolute inset-0 bg-cover bg-center bg-no-repeat"
        style={{
          backgroundImage: "url('/mandaluyong-clinic-san-farael.JPG')",
        }}
      >
        {/* Dark overlay for text readability */}
        <div className="absolute inset-0 bg-black/40"></div>
      </div>

      <div className="relative z-10 w-full h-full flex items-center justify-center px-4">
        <div className="max-w-4xl mx-auto text-center text-white">
          <h1 className="text-4xl md:text-6xl font-bold mb-6 leading-tight text-balance">
            Professional <span className="text-primary">Dental Care</span> in Mandaluyong
          </h1>
          <p className="text-lg md:text-xl text-white/90 mb-8 max-w-2xl mx-auto text-balance">
            At San Rafael Dental Center in Mandaluyong City, we provide quality dental care to families in Plainview, Shaw Boulevard, and surrounding Metro Manila communities since 1999.
          </p>
          <div className="flex flex-col sm:flex-row gap-4 justify-center mb-12">
            <a
              href="#appointment"
              className="px-8 py-3 bg-primary text-primary-foreground rounded-lg font-semibold hover:bg-primary/90 transition-colors"
            >
              Book Appointment
            </a>
            <a
              href="#services"
              className="px-8 py-3 border-2 text-white rounded-lg font-semibold hover:bg-white/10 transition-colors border-primary"
            >
              Learn More
            </a>
          </div>

          {/* Stats */}
          <div className="grid grid-cols-2 gap-8 md:gap-16 max-w-sm mx-auto">
            <div>
              <div className="text-3xl md:text-4xl font-bold text-card">25+</div>
              <div className="text-sm text-white/80">Years Experience</div>
            </div>
            <div>
              <div className="text-3xl md:text-4xl font-bold text-card">5000+</div>
              <div className="text-sm text-white/80">Happy Patients</div>
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}
