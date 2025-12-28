"use client"
import Header from "@/components/header"
import Hero from "@/components/hero"
import Services from "@/components/services"
import AppointmentForm from "@/components/appointment-form"
import Testimonials from "@/components/testimonials"
import Gallery from "@/components/gallery"
import LocationMap from "@/components/location-map"
import FAQ from "@/components/faq"
import Footer from "@/components/footer"
import Script from "next/script"

export default function Home() {
  const jsonLd = {
    "@context": "https://schema.org",
    "@type": "DentalClinic",
    "name": "San Rafael Dental Clinic",
    "alternateName": "San Rafael Dental Care Mandaluyong",
    "description": "Leading dental clinic in Mandaluyong City offering comprehensive dental services including cleanings, cosmetic dentistry, implants, and emergency care.",
    "url": "https://sanrafaeldental.com",
    "telephone": "+639178014040",
    "email": "hello@srdentalcare.com",
    "priceRange": "$$",
    "currenciesAccepted": "PHP",
    "paymentAccepted": "Cash, Credit Card, Insurance",
    "founder": {
      "@type": "Person",
      "name": "Dr. Ronald Remulla"
    },
    "address": {
      "@type": "PostalAddress",
      "streetAddress": "93B San Rafael",
      "addressLocality": "Plainview, Mandaluyong",
      "addressRegion": "Metro Manila",
      "postalCode": "1550",
      "addressCountry": "PH"
    },
    "geo": {
      "@type": "GeoCoordinates",
      "latitude": "14.5833",
      "longitude": "121.0333"
    },
    "openingHoursSpecification": [
      {
        "@type": "OpeningHoursSpecification",
        "dayOfWeek": ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"],
        "opens": "08:00",
        "closes": "18:00"
      },
      {
        "@type": "OpeningHoursSpecification",
        "dayOfWeek": "Saturday",
        "opens": "09:00",
        "closes": "14:00"
      }
    ],
    "hasOfferCatalog": {
      "@type": "OfferCatalog",
      "name": "Dental Services",
      "itemListElement": [
        {
          "@type": "Offer",
          "itemOffered": {
            "@type": "Service",
            "name": "Professional Dental Cleaning",
            "description": "Complete oral prophylaxis and dental hygiene services"
          }
        },
        {
          "@type": "Offer",
          "itemOffered": {
            "@type": "Service",
            "name": "Cosmetic Dentistry",
            "description": "Teeth whitening, veneers, and smile enhancement procedures"
          }
        },
        {
          "@type": "Offer",
          "itemOffered": {
            "@type": "Service",
            "name": "Dental Implants",
            "description": "Permanent tooth replacement solutions"
          }
        },
        {
          "@type": "Offer",
          "itemOffered": {
            "@type": "Service",
            "name": "Restorative Dentistry",
            "description": "Fillings, crowns, root canals, and dental restoration"
          }
        }
      ]
    },
    "aggregateRating": {
      "@type": "AggregateRating",
      "ratingValue": "4.8",
      "reviewCount": "150",
      "bestRating": "5",
      "worstRating": "1"
    },
    "review": [
      {
        "@type": "Review",
        "reviewRating": {
          "@type": "Rating",
          "ratingValue": "5",
          "bestRating": "5"
        },
        "author": {
          "@type": "Person",
          "name": "Maria Santos"
        },
        "reviewBody": "Excellent dental care in Mandaluyong! Dr. Ronald Remulla and the team provided outstanding service for my family's dental needs."
      }
    ],
    "areaServed": [
      {
        "@type": "City",
        "name": "Mandaluyong City"
      },
      {
        "@type": "Place",
        "name": "Plainview, Mandaluyong"
      },
      {
        "@type": "Place",
        "name": "Shaw Boulevard Area"
      },
      {
        "@type": "Place",
        "name": "Metro Manila"
      }
    ],
    "sameAs": [
      "https://www.facebook.com/profile.php?id=100084249220187"
    ]
  }

  return (
    <>
      <Script
        id="dental-clinic-jsonld"
        type="application/ld+json"
        dangerouslySetInnerHTML={{
          __html: JSON.stringify(jsonLd),
        }}
      />
      <main className="flex flex-col">
        <Header />
        <Hero />
        <Services />
        <AppointmentForm />
        <Testimonials />
        <Gallery />
        <LocationMap />
        <FAQ />
        <Footer />
      </main>
    </>
  )
}
