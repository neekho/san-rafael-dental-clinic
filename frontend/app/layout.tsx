import type React from "react"
import type { Metadata } from "next"
import { Geist, Geist_Mono } from "next/font/google"
import { Analytics } from "@vercel/analytics/next"
import "./globals.css"

const _geist = Geist({ subsets: ["latin"] })
const _geistMono = Geist_Mono({ subsets: ["latin"] })

export const metadata: Metadata = {
  title: {
    default: "San Rafael Dental Clinic Mandaluyong - Best Dentist in Plainview",
    template: "%s | San Rafael Dental Clinic Mandaluyong"
  },
  description: "Leading dental clinic in Mandaluyong City offering professional dental services including cleanings, fillings, root canals, crowns, implants, and cosmetic dentistry. Serving Plainview, Shaw Boulevard, and surrounding Metro Manila areas since 1999.",
  keywords: [
    "dentist Mandaluyong",
    "dental clinic Mandaluyong City",
    "Plainview dentist",
    "Shaw Boulevard dental",
    "Metro Manila dentist",
    "dental cleaning Mandaluyong",
    "root canal Mandaluyong",
    "dental implants Mandaluyong",
    "cosmetic dentistry Mandaluyong",
    "tooth extraction Mandaluyong",
    "dental checkup Mandaluyong",
    "San Rafael dental clinic"
  ],
  authors: [{ name: "San Rafael Dental Clinic" }],
  creator: "San Rafael Dental Clinic",
  publisher: "San Rafael Dental Clinic",
  formatDetection: {
    email: false,
    address: false,
    telephone: false,
  },
  metadataBase: new URL("https://sanrafaeldental.com"),
  alternates: {
    canonical: "/",
  },
  openGraph: {
    title: "San Rafael Dental Clinic - Premier Dentist in Mandaluyong City",
    description: "Expert dental care in Mandaluyong's Plainview area. Professional cleanings, cosmetic dentistry, implants & more. Book your appointment today!",
    url: "https://sanrafaeldental.com",
    siteName: "San Rafael Dental Clinic",
    locale: "en_PH",
    type: "website",
    images: [
      {
        url: "/modern-dental-office.png",
        width: 1200,
        height: 630,
        alt: "San Rafael Dental Clinic - Modern dental office in Mandaluyong",
      },
    ],
  },
  twitter: {
    card: "summary_large_image",
    title: "San Rafael Dental Clinic - Best Dentist in Mandaluyong",
    description: "Professional dental services in Mandaluyong City. Expert care for cleanings, cosmetic dentistry, implants & more.",
    images: ["/modern-dental-office.png"],
  },
  robots: {
    index: true,
    follow: true,
    googleBot: {
      index: true,
      follow: true,
      "max-video-preview": -1,
      "max-image-preview": "large",
      "max-snippet": -1,
    },
  },
  verification: {
    google: "your-google-verification-code",
  },
  generator: "Next.js",
  applicationName: "San Rafael Dental Clinic",
  referrer: "origin-when-cross-origin",
  category: "Healthcare",
  classification: "Dental Services",
  icons: {
    icon: [
      {
        url: "/logo.png?v=3",
        sizes: "32x32",
        type: "image/png",
      },
      {
        url: "/logo.png?v=3",
        sizes: "16x16",
        type: "image/png",
      },
    ],
    apple: "/logo?v=3",
    shortcut: "/logo.png?v=3",
  },
}

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode
}>) {
  return (
    <html lang="en-PH">
      <body className="font-sans antialiased">
        {children}
        <Analytics />
      </body>
    </html>
  )
}
