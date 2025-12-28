"use client"

import { useState } from "react"

const galleryImages = [
  {
    id: 1,
    title: "Teeth Whitening Before & After - Mandaluyong",
    image: "/san-rafael-dental-clinic-mandaluyong-plainview-1.jpg",
  },
  {
    id: 2,
    title: "Cosmetic Dentistry Transformation - San Rafael Dental",
    image: "/san-rafael-dental-clinic-mandaluyong-plainview-3.jpg",
  },
  {
    id: 3,
    title: "Modern Dental Office in Plainview, Mandaluyong",
    image: "/san-rafael-guests.jpg",
  },
  {
    id: 4,
    title: "Advanced Treatment Room - San Rafael Dental Mandaluyong",
    image: "/san-rafael-before-and-after-dental-work.jpg",
  }
]

export default function Gallery() {
  const [selectedImage, setSelectedImage] = useState<number | null>(null)

  return (
    <section id="gallery" className="py-20 md:py-32 bg-primary/5">
      <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="text-center mb-16">
          <h2 className="text-3xl md:text-5xl font-bold text-foreground mb-4">Gallery - Mandaluyong's Premier Dental Clinic</h2>
          <p className="text-lg text-muted-foreground">See the beautiful smile transformations and modern facilities</p>
        </div>

        {/* Gallery Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-2 gap-6">
          {galleryImages.map((item) => (
            <div
              key={item.id}
              onClick={() => setSelectedImage(item.id)}
              className="relative overflow-hidden rounded-xl cursor-pointer group h-80 md:h-96"
            >
              <img
                src={item.image || "/placeholder.svg"}
                alt={item.title}
                className="w-full h-full object-cover transition-transform duration-300 group-hover:scale-110"
              />
              <div className="Vduration-300 flex items-center justify-center">
                <span className="text-white font-semibold opacity-0 group-hover:opacity-100 transition-opacity">
                  View
                </span>
              </div>
            </div>
          ))}
        </div>

        {/* Lightbox Modal */}
        {selectedImage && (
          <div
            className="fixed inset-0 bg-black/80 z-50 flex items-center justify-center p-4"
            onClick={() => setSelectedImage(null)}
          >
            <div className="relative max-w-4xl w-full" onClick={(e) => e.stopPropagation()}>
              <img
                src={galleryImages.find((img) => img.id === selectedImage)?.image || "/placeholder.svg"}
                alt={`${galleryImages.find((img) => img.id === selectedImage)?.title} - San Rafael Dental Clinic Mandaluyong`}
                className="w-full h-auto rounded-lg"
              />
              <button
                onClick={() => setSelectedImage(null)}
                className="absolute top-4 right-4 bg-white/90 hover:bg-white rounded-full p-2 transition-colors"
              >
                <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                </svg>
              </button>
            </div>
          </div>
        )}
      </div>
    </section>
  )
}
