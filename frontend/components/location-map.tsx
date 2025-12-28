export default function LocationMap() {
  return (
    <section id="location" className="py-16 bg-background shadow-xl">
      <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center mb-12">
          <h2 className="text-4xl font-bold text-foreground mb-2">Visit San Rafael Dental Clinic in Mandaluyong</h2>
          <p className="text-lg text-muted-foreground">Easy access from Shaw Boulevard and Metro Manila</p>
        </div>

        {/* Map Container */}
        <div className="rounded-lg overflow-hidden shadow-lg h-96">
          <iframe
            width="100%"
            height="100%"
            style={{ border: 0 }}
            loading="lazy"
            allowFullScreen
            referrerPolicy="no-referrer-when-downgrade"
            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3860.5493869381276!2d121.54321!3d14.58333!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3397b7d5d5d5d5d5%3A0x0!2s93B%20San%20Rafael%20Street%2C%20Plainview%2C%20Mandaluyong%2C%201550!5e0!3m2!1sen!2sph!4v1234567890123"
          />
        </div>

        <div className="mt-8 text-center">
          <p className="text-muted-foreground mb-4">
            San Rafael Dental Clinic - Premier Dentist in Mandaluyong
            <br />
            93B San Rafael Street, Plainview, Mandaluyong City
            <br />
            Metro Manila, Philippines 1550
            <br />
          </p>
          <a
            href="https://www.google.com/maps/search/93B+San+Rafael+Street,+Plainview,+Mandaluyong,+Philippines"
            target="_blank"
            rel="noopener noreferrer"
            className="inline-block bg-primary text-primary-foreground px-6 py-2 rounded-lg hover:bg-primary/90 transition-colors"
          >
            Get Directions
          </a>
        </div>
      </div>
    </section>
  )
}
