package com.example.data.demo

import com.example.data.model.ApprovalStatus
import com.example.data.model.Category
import com.example.data.model.City
import com.example.data.model.Provider
import com.example.data.model.RequestStatus
import com.example.data.model.Review
import com.example.data.model.ServiceItem
import com.example.data.model.ServiceRequest
import com.example.data.model.UserAccount
import com.example.data.model.UserRole

object SeedDemoData {

  val cities = listOf(
    City(
      id = "jaipur",
      name = "Jaipur",
      state = "Rajasthan",
      isActive = true,
      areas = listOf(
        "Vaishali Nagar",
        "Mansarovar",
        "Malviya Nagar",
        "Jagatpura",
        "C-Scheme",
        "Raja Park",
        "Sodala",
        "Bani Park",
        "Vidyadhar Nagar",
        "Pratap Nagar"
      )
    ),
    City(
      id = "jamnagar",
      name = "Jamnagar",
      state = "Gujarat",
      isActive = true,
      areas = listOf(
        "Patel Colony",
        "Digjam Circle",
        "Khambhalia Gate",
        "Bedi Port Road",
        "Oswal Colony",
        "Park Colony",
        "Gurudwara Road",
        "Samarpan Circle"
      )
    )
  )

  val categories = listOf(
    Category(
      id = "cat_ac_repair",
      name = "AC & Refrigerator Repair",
      iconName = "ac_unit",
      description = "AC installation, cooling issues, filter cleaning & fridge repair",
      isActive = true,
      displayOrder = 1,
      bannerHexColor = "#0284C7"
    ),
    Category(
      id = "cat_electrician",
      name = "Electrician",
      iconName = "bolt",
      description = "Wiring, switchboard fixes, inverter setup & appliance installation",
      isActive = true,
      displayOrder = 2,
      bannerHexColor = "#D97706"
    ),
    Category(
      id = "cat_plumber",
      name = "Plumber",
      iconName = "plumbing",
      description = "Pipe repairs, tap fitting, tank leakage & bathroom fittings",
      isActive = true,
      displayOrder = 3,
      bannerHexColor = "#2563EB"
    ),
    Category(
      id = "cat_food_sweets",
      name = "Food & Sweets",
      iconName = "restaurant",
      description = "Authentic local sweets, namkeen, party catering & bakery items",
      isActive = true,
      displayOrder = 4,
      bannerHexColor = "#EA580C"
    ),
    Category(
      id = "cat_pharmacy",
      name = "Pharmacy / Medical",
      iconName = "medical_services",
      description = "Prescription medicines, wellness essentials & first aid",
      isActive = true,
      displayOrder = 5,
      bannerHexColor = "#059669"
    ),
    Category(
      id = "cat_cleaning",
      name = "Cleaning",
      iconName = "cleaning_services",
      description = "Deep home cleaning, sofa sanitation, kitchen & bathroom wash",
      isActive = true,
      displayOrder = 6,
      bannerHexColor = "#7C3AED"
    ),
    Category(
      id = "cat_salon_beauty",
      name = "Salon & Beauty",
      iconName = "face",
      description = "Home haircuts, facials, waxing, bridal grooming & spa treatments",
      isActive = true,
      displayOrder = 7,
      bannerHexColor = "#DB2777"
    ),
    Category(
      id = "cat_bike_car",
      name = "Bike & Car Service",
      iconName = "directions_car",
      description = "Two-wheeler tuning, car doorstep wash, oil change & puncture assist",
      isActive = true,
      displayOrder = 8,
      bannerHexColor = "#475569"
    ),
    Category(
      id = "cat_electronics",
      name = "Electronics",
      iconName = "tv",
      description = "LED TV repair, microwave, washing machine & home theater fix",
      isActive = true,
      displayOrder = 9,
      bannerHexColor = "#0D9488"
    ),
    Category(
      id = "cat_mobile_computer",
      name = "Mobile & Computer",
      iconName = "devices",
      description = "Smartphone screen replacement, laptop tune-up & data recovery",
      isActive = true,
      displayOrder = 10,
      bannerHexColor = "#4F46E5"
    ),
    Category(
      id = "cat_grocery",
      name = "Grocery",
      iconName = "shopping_cart",
      description = "Fresh vegetables, local pulses, spices & monthly rations",
      isActive = true,
      displayOrder = 11,
      bannerHexColor = "#16A34A"
    ),
    Category(
      id = "cat_painter",
      name = "Painter",
      iconName = "format_paint",
      description = "Interior wall painting, texture finish, waterproof coating",
      isActive = true,
      displayOrder = 12,
      bannerHexColor = "#CA8A04"
    ),
    Category(
      id = "cat_transport",
      name = "Local Transport",
      iconName = "local_shipping",
      description = "Mini-truck luggage shifting, local tempo & pickup delivery",
      isActive = true,
      displayOrder = 13,
      bannerHexColor = "#9333EA"
    ),
    Category(
      id = "cat_photographer",
      name = "Photographer",
      iconName = "camera_alt",
      description = "Event photography, pre-wedding shoots, family portraits & video",
      isActive = true,
      displayOrder = 14,
      bannerHexColor = "#BE185D"
    ),
    Category(
      id = "cat_home_tutor",
      name = "Home Tutor",
      iconName = "school",
      description = "CBSE/State board tutoring, Math, Science & English coaching",
      isActive = true,
      displayOrder = 15,
      bannerHexColor = "#1D4ED8"
    ),
    Category(
      id = "cat_home_services",
      name = "Home Services",
      iconName = "home_repair_service",
      description = "Carpentry, pest control, locksmith & general handyman",
      isActive = true,
      displayOrder = 16,
      bannerHexColor = "#65A30D"
    ),
    Category(
      id = "cat_other_services",
      name = "Other Local Services",
      iconName = "build",
      description = "Tailoring, ironing, key making & verified custom local helpers",
      isActive = true,
      displayOrder = 17,
      bannerHexColor = "#64748B"
    )
  )

  val providers = listOf(
    // JAIPUR PROVIDERS
    Provider(
      id = "prov_jpr_ac_1",
      businessName = "Rajasthan Cool Care & AC Solutions",
      ownerName = "Mahesh Sharma",
      phone = "+91 98290 12345",
      email = "mahesh.coolcare@example.com",
      cityId = "jaipur",
      cityName = "Jaipur",
      serviceAreas = listOf("Vaishali Nagar", "Mansarovar", "Sodala", "C-Scheme"),
      categoryIds = listOf("cat_ac_repair", "cat_electronics"),
      description = "12+ years of certified HVAC experience in Jaipur. Specialized in Split & Window AC deep foam cleaning, gas filling, and compressor servicing.",
      startingPrice = 299.0,
      experienceYears = 12,
      rating = 4.9,
      reviewCount = 128,
      isAvailable = true,
      approvalStatus = ApprovalStatus.APPROVED,
      address = "42, Queens Road, Vaishali Nagar, Jaipur"
    ),
    Provider(
      id = "prov_jpr_elec_1",
      businessName = "Jaipur City Spark Electricals",
      ownerName = "Rajendra Meena",
      phone = "+91 98291 54321",
      email = "rajendra.spark@example.com",
      cityId = "jaipur",
      cityName = "Jaipur",
      serviceAreas = listOf("Malviya Nagar", "Jagatpura", "Pratap Nagar", "Raja Park"),
      categoryIds = listOf("cat_electrician", "cat_home_services"),
      description = "Fast 30-minute doorstep service for short circuits, inverter wiring, smart switches, and full house electrical safety audits.",
      startingPrice = 149.0,
      experienceYears = 8,
      rating = 4.8,
      reviewCount = 94,
      isAvailable = true,
      approvalStatus = ApprovalStatus.APPROVED,
      address = "Shop 14, Calgiri Marg, Malviya Nagar, Jaipur"
    ),
    Provider(
      id = "prov_jpr_sweet_1",
      businessName = "Kanha Royal Sweets & Namkeen",
      ownerName = "Suresh Agarwal",
      phone = "+91 94140 98765",
      email = "kanha.sweets@example.com",
      cityId = "jaipur",
      cityName = "Jaipur",
      serviceAreas = listOf("C-Scheme", "Bani Park", "Raja Park", "Vaishali Nagar", "Mansarovar"),
      categoryIds = listOf("cat_food_sweets"),
      description = "Legendary Jaipur Ghewar, Pyaaz Kachori, Mawa Kachori, and pure desi ghee festive sweets delivered fresh with hygienic packaging.",
      startingPrice = 180.0,
      experienceYears = 24,
      rating = 4.9,
      reviewCount = 312,
      isAvailable = true,
      approvalStatus = ApprovalStatus.APPROVED,
      fssaiNumber = "12219026000452",
      fssaiDocumentUrl = "fssai_cert_kanha_jpr.pdf",
      isFssaiVerified = true,
      address = "Plot 8, Bhagwan Das Road, C-Scheme, Jaipur"
    ),
    Provider(
      id = "prov_jpr_pharm_1",
      businessName = "Sanjeevani Medicos & Wellness",
      ownerName = "Dr. Amit Verma",
      phone = "+91 98293 88888",
      email = "sanjeevani.jpr@example.com",
      cityId = "jaipur",
      cityName = "Jaipur",
      serviceAreas = listOf("Mansarovar", "Sodala", "Vaishali Nagar"),
      categoryIds = listOf("cat_pharmacy"),
      description = "Govt. Licensed Pharmacy offering fast genuine medicine delivery, surgical disposables, BP/Sugar monitors, and baby care essentials.",
      startingPrice = 99.0,
      experienceYears = 15,
      rating = 4.8,
      reviewCount = 86,
      isAvailable = true,
      approvalStatus = ApprovalStatus.APPROVED,
      drugLicenceNumber = "RJ-JPR-20B-78912",
      drugLicenceDocUrl = "drug_licence_sanjeevani_jpr.pdf",
      isDrugLicenceVerified = true,
      address = "Sector 3, Varun Path, Mansarovar, Jaipur"
    ),
    Provider(
      id = "prov_jpr_clean_1",
      businessName = "ShinePink Urban Home Cleaners",
      ownerName = "Pooja Rathore",
      phone = "+91 98294 11223",
      email = "shinepink.clean@example.com",
      cityId = "jaipur",
      cityName = "Jaipur",
      serviceAreas = listOf("Vaishali Nagar", "Malviya Nagar", "Jagatpura", "Vidyadhar Nagar"),
      categoryIds = listOf("cat_cleaning", "cat_home_services"),
      description = "Professional deep cleaning with German high-pressure machines, non-toxic eco chemicals, and trained background-verified staff.",
      startingPrice = 499.0,
      experienceYears = 6,
      rating = 4.7,
      reviewCount = 73,
      isAvailable = true,
      approvalStatus = ApprovalStatus.APPROVED,
      address = "Sector 7, Vidyadhar Nagar, Jaipur"
    ),
    Provider(
      id = "prov_jpr_salon_1",
      businessName = "Glamour Touch Home Salon & Bridal",
      ownerName = "Neetu Kanwar",
      phone = "+91 98295 77665",
      email = "glamour.neetu@example.com",
      cityId = "jaipur",
      cityName = "Jaipur",
      serviceAreas = listOf("Raja Park", "C-Scheme", "Bani Park", "Malviya Nagar"),
      categoryIds = listOf("cat_salon_beauty"),
      description = "Premium home salon services for ladies. Single-use kits, branded cosmetics (O3+, L'Oreal, MAC), waxing, facial, and party makeup.",
      startingPrice = 349.0,
      experienceYears = 9,
      rating = 4.9,
      reviewCount = 145,
      isAvailable = true,
      approvalStatus = ApprovalStatus.APPROVED,
      address = "Lane 4, Raja Park, Jaipur"
    ),
    // JAMNAGAR PROVIDERS
    Provider(
      id = "prov_jmn_ac_1",
      businessName = "Jamnagar ChillPoint Refrigeration",
      ownerName = "Bhavin Patel",
      phone = "+91 98250 11223",
      email = "bhavin.chillpoint@example.com",
      cityId = "jamnagar",
      cityName = "Jamnagar",
      serviceAreas = listOf("Patel Colony", "Digjam Circle", "Khambhalia Gate", "Park Colony"),
      categoryIds = listOf("cat_ac_repair", "cat_electronics"),
      description = "Top rated cooling expert in Jamnagar. Fast gas leakage detection, jet pump AC wash, copper pipe insulation, and deep fridge service.",
      startingPrice = 249.0,
      experienceYears = 10,
      rating = 4.8,
      reviewCount = 67,
      isAvailable = true,
      approvalStatus = ApprovalStatus.APPROVED,
      address = "Plot 18, Patel Colony Road No. 3, Jamnagar"
    ),
    Provider(
      id = "prov_jmn_food_1",
      businessName = "Halar Kathiyawadi & Farsan Mart",
      ownerName = "Kishore Jadeja",
      phone = "+91 98252 33445",
      email = "halar.farsan@example.com",
      cityId = "jamnagar",
      cityName = "Jamnagar",
      serviceAreas = listOf("Khambhalia Gate", "Digjam Circle", "Oswal Colony", "Gurudwara Road"),
      categoryIds = listOf("cat_food_sweets"),
      description = "Jamnagar's famous Dry Fruit Kachori, Gathiya, Jalebi, Methi Pak, and authentic snacks made fresh everyday in pure groundnut oil.",
      startingPrice = 120.0,
      experienceYears = 18,
      rating = 4.9,
      reviewCount = 210,
      isAvailable = true,
      approvalStatus = ApprovalStatus.APPROVED,
      fssaiNumber = "10718015000213",
      fssaiDocumentUrl = "fssai_cert_halar_jmn.pdf",
      isFssaiVerified = true,
      address = "Near Khambhalia Gate Tower, Jamnagar"
    ),
    Provider(
      id = "prov_jmn_pharm_1",
      businessName = "Jamnagar City Pharmacy & Surgicals",
      ownerName = "Haresh Mehta",
      phone = "+91 98254 55667",
      email = "haresh.meds@example.com",
      cityId = "jamnagar",
      cityName = "Jamnagar",
      serviceAreas = listOf("Samarpan Circle", "Patel Colony", "Park Colony", "Bedi Port Road"),
      categoryIds = listOf("cat_pharmacy"),
      description = "Reliable 24x7 local pharmacy. Complete range of cardiac, diabetic, emergency medicines and home health equipment.",
      startingPrice = 80.0,
      experienceYears = 14,
      rating = 4.7,
      reviewCount = 59,
      isAvailable = true,
      approvalStatus = ApprovalStatus.APPROVED,
      drugLicenceNumber = "GJ-JAM-20B-43211",
      drugLicenceDocUrl = "drug_licence_jmn_city.pdf",
      isDrugLicenceVerified = true,
      address = "Opp. Samarpan Hospital, Jamnagar"
    ),
    Provider(
      id = "prov_jmn_plumb_1",
      businessName = "Dwarka Pro Plumbing & Motor Fix",
      ownerName = "Mukesh Vaghela",
      phone = "+91 98256 77889",
      email = "mukesh.plumbing@example.com",
      cityId = "jamnagar",
      cityName = "Jamnagar",
      serviceAreas = listOf("Digjam Circle", "Oswal Colony", "Gurudwara Road"),
      categoryIds = listOf("cat_plumber", "cat_home_services"),
      description = "Expert sanitary fitting, water motor installation, pipeline leak repair, overhead tank cleaning, and CPVC pipe replacements.",
      startingPrice = 199.0,
      experienceYears = 7,
      rating = 4.6,
      reviewCount = 42,
      isAvailable = true,
      approvalStatus = ApprovalStatus.APPROVED,
      address = "Digjam Mill Compound, Jamnagar"
    ),
    // PENDING PROVIDERS FOR ADMIN VERIFICATION WORKFLOW
    Provider(
      id = "prov_pending_sweet_1",
      businessName = "Gokul Dairy & Rasgulla Center",
      ownerName = "Govind Saini",
      phone = "+91 98299 99001",
      email = "govind.gokul@example.com",
      cityId = "jaipur",
      cityName = "Jaipur",
      serviceAreas = listOf("Jagatpura", "Pratap Nagar"),
      categoryIds = listOf("cat_food_sweets"),
      description = "Fresh paneer, desi ghee rasgulla, curd, and party dairy supply.",
      startingPrice = 150.0,
      experienceYears = 3,
      rating = 5.0,
      reviewCount = 0,
      isAvailable = true,
      approvalStatus = ApprovalStatus.PENDING,
      fssaiNumber = "12223012000889",
      fssaiDocumentUrl = "fssai_gokul_pending.pdf",
      isFssaiVerified = false,
      address = "Main Market, Jagatpura, Jaipur"
    ),
    Provider(
      id = "prov_pending_med_1",
      businessName = "Arogya Care Med Store",
      ownerName = "Pravin Solanki",
      phone = "+91 98259 44332",
      email = "pravin.arogya@example.com",
      cityId = "jamnagar",
      cityName = "Jamnagar",
      serviceAreas = listOf("Bedi Port Road", "Patel Colony"),
      categoryIds = listOf("cat_pharmacy"),
      description = "New neighborhood dispensary offering OTC medicines and health drinks.",
      startingPrice = 100.0,
      experienceYears = 2,
      rating = 5.0,
      reviewCount = 0,
      isAvailable = true,
      approvalStatus = ApprovalStatus.PENDING,
      drugLicenceNumber = "GJ-JAM-21B-99881",
      drugLicenceDocUrl = "drug_licence_arogya_pending.pdf",
      isDrugLicenceVerified = false,
      address = "Bedi Port Main Road, Jamnagar"
    )
  )

  val services = listOf(
    // Services for Cool Care Jaipur
    ServiceItem(
      id = "srv_ac_1",
      providerId = "prov_jpr_ac_1",
      cityId = "jaipur",
      categoryId = "cat_ac_repair",
      title = "Split AC Jet Deep Foam Cleaning",
      description = "Deep coil cleaning with high pressure water jet, filter wash, tray disinfection, and blower clean.",
      price = 399.0,
      priceUnit = "per unit",
      estimatedTime = "45 mins"
    ),
    ServiceItem(
      id = "srv_ac_2",
      providerId = "prov_jpr_ac_1",
      cityId = "jaipur",
      categoryId = "cat_ac_repair",
      title = "AC Gas Refilling & Leak Test",
      description = "Nitrogen pressure leak test, brazing fix, vacuuming, and complete R32/R410A gas top-up with cooling warranty.",
      price = 1499.0,
      priceUnit = "per AC",
      estimatedTime = "60-90 mins"
    ),
    ServiceItem(
      id = "srv_ac_3",
      providerId = "prov_jpr_ac_1",
      cityId = "jaipur",
      categoryId = "cat_ac_repair",
      title = "Window AC Installation / Uninstallation",
      description = "Standard mounting, bracket securing, insulation seal, and live cooling demo.",
      price = 499.0,
      priceUnit = "per AC",
      estimatedTime = "60 mins"
    ),
    // Services for Electrician Jaipur
    ServiceItem(
      id = "srv_elec_1",
      providerId = "prov_jpr_elec_1",
      cityId = "jaipur",
      categoryId = "cat_electrician",
      title = "Switchboard & MCB Troubleshooting",
      description = "Replacement of faulty switches, MCB tripping repair, and earthing diagnostic check.",
      price = 199.0,
      priceUnit = "per visit",
      estimatedTime = "30 mins"
    ),
    ServiceItem(
      id = "srv_elec_2",
      providerId = "prov_jpr_elec_1",
      cityId = "jaipur",
      categoryId = "cat_electrician",
      title = "Inverter & Battery Wiring Setup",
      description = "Safe installation and load division for home inverter battery setup.",
      price = 349.0,
      priceUnit = "per setup",
      estimatedTime = "45 mins"
    ),
    // Services for Kanha Sweets Jaipur
    ServiceItem(
      id = "srv_swt_1",
      providerId = "prov_jpr_sweet_1",
      cityId = "jaipur",
      categoryId = "cat_food_sweets",
      title = "Authentic Jaipur Special Ghewar (1 Kg)",
      description = "Traditional honeycomb sweet soaked in saffron sugar syrup with crunchy pistachios and almonds.",
      price = 580.0,
      priceUnit = "1 Kg Box",
      estimatedTime = "Delivery in 30 mins"
    ),
    ServiceItem(
      id = "srv_swt_2",
      providerId = "prov_jpr_sweet_1",
      cityId = "jaipur",
      categoryId = "cat_food_sweets",
      title = "Crispy Pyaaz Kachori (Box of 4)",
      description = "Spicy caramelized onion filling in crisp flaky crust, served with sweet tamarind and mint chutney.",
      price = 160.0,
      priceUnit = "Pack of 4",
      estimatedTime = "Delivery in 25 mins"
    ),
    // Services for Sanjeevani Pharmacy Jaipur
    ServiceItem(
      id = "srv_med_1",
      providerId = "prov_jpr_pharm_1",
      cityId = "jaipur",
      categoryId = "cat_pharmacy",
      title = "Doctor Prescription Medicine Pack Fulfillment",
      description = "Upload or order your prescribed medicines delivered directly by licensed pharmacist.",
      price = 250.0,
      priceUnit = "order value basis",
      estimatedTime = "Fast 20 mins"
    ),
    ServiceItem(
      id = "srv_med_2",
      providerId = "prov_jpr_pharm_1",
      cityId = "jaipur",
      categoryId = "cat_pharmacy",
      title = "Digital BP Monitor & Sugar Test Kit",
      description = "Branded automated blood pressure cuff and glucometer with 25 test strips.",
      price = 1250.0,
      priceUnit = "per kit",
      estimatedTime = "Same day delivery"
    ),
    // Services for Jamnagar Farsan
    ServiceItem(
      id = "srv_jmn_farsan_1",
      providerId = "prov_jmn_food_1",
      cityId = "jamnagar",
      categoryId = "cat_food_sweets",
      title = "Famous Jamnagar Dryfruit Kachori (500g)",
      description = "Legendary dry fruit filled crunchy kachori that stays fresh for up to 30 days.",
      price = 260.0,
      priceUnit = "500g Box",
      estimatedTime = "Delivery in 30 mins"
    ),
    ServiceItem(
      id = "srv_jmn_farsan_2",
      providerId = "prov_jmn_food_1",
      cityId = "jamnagar",
      categoryId = "cat_food_sweets",
      title = "Fresh Bhavnagri Gathiya & Papdi (500g)",
      description = "Melt-in-mouth soft gathiya served with spicy raw papaya sambharo and fried green chilies.",
      price = 150.0,
      priceUnit = "500g",
      estimatedTime = "Fresh hot prep"
    ),
    // Services for Jamnagar ChillPoint
    ServiceItem(
      id = "srv_jmn_ac_1",
      providerId = "prov_jmn_ac_1",
      cityId = "jamnagar",
      categoryId = "cat_ac_repair",
      title = "Complete AC Jet Clean & Anti-Rust Coating",
      description = "Special coastal anti-rust chemical treatment on condenser coils + foam jet wash.",
      price = 450.0,
      priceUnit = "per unit",
      estimatedTime = "45 mins"
    )
  )

  val sampleRequests = listOf(
    ServiceRequest(
      id = "req_101",
      customerId = "user_cust_1",
      customerName = "Vikas Singhal",
      customerPhone = "+91 97845 12000",
      customerAddress = "B-12, Gautam Marg, Vaishali Nagar",
      cityId = "jaipur",
      cityName = "Jaipur",
      area = "Vaishali Nagar",
      providerId = "prov_jpr_ac_1",
      providerName = "Rajasthan Cool Care & AC Solutions",
      providerPhone = "+91 98290 12345",
      categoryId = "cat_ac_repair",
      categoryName = "AC & Refrigerator Repair",
      serviceId = "srv_ac_1",
      serviceName = "Split AC Jet Deep Foam Cleaning",
      price = 399.0,
      quantity = 2,
      totalAmount = 798.0,
      preferredDate = "Today",
      preferredTime = "04:00 PM",
      descriptionNotes = "Two split ACs in master bedroom and living room need water jet cleaning before summer.",
      status = RequestStatus.IN_PROGRESS,
      createdAt = System.currentTimeMillis() - 7200000
    ),
    ServiceRequest(
      id = "req_102",
      customerId = "user_cust_1",
      customerName = "Vikas Singhal",
      customerPhone = "+91 97845 12000",
      customerAddress = "B-12, Gautam Marg, Vaishali Nagar",
      cityId = "jaipur",
      cityName = "Jaipur",
      area = "Vaishali Nagar",
      providerId = "prov_jpr_sweet_1",
      providerName = "Kanha Royal Sweets & Namkeen",
      providerPhone = "+91 94140 98765",
      categoryId = "cat_food_sweets",
      categoryName = "Food & Sweets",
      serviceId = "srv_swt_1",
      serviceName = "Authentic Jaipur Special Ghewar (1 Kg)",
      price = 580.0,
      quantity = 1,
      totalAmount = 580.0,
      preferredDate = "Yesterday",
      preferredTime = "06:00 PM",
      descriptionNotes = "Gift packaging for family gathering.",
      status = RequestStatus.COMPLETED,
      createdAt = System.currentTimeMillis() - 86400000
    ),
    ServiceRequest(
      id = "req_103",
      customerId = "user_cust_2",
      customerName = "Ananya Sharma",
      customerPhone = "+91 94145 66778",
      customerAddress = "45, Malviya Nagar Sector 3",
      cityId = "jaipur",
      cityName = "Jaipur",
      area = "Malviya Nagar",
      providerId = "prov_jpr_elec_1",
      providerName = "Jaipur City Spark Electricals",
      providerPhone = "+91 98291 54321",
      categoryId = "cat_electrician",
      categoryName = "Electrician",
      serviceId = "srv_elec_1",
      serviceName = "Switchboard & MCB Troubleshooting",
      price = 199.0,
      quantity = 1,
      totalAmount = 199.0,
      preferredDate = "Tomorrow",
      preferredTime = "11:00 AM",
      descriptionNotes = "Kitchen switch spark check.",
      status = RequestStatus.PENDING,
      createdAt = System.currentTimeMillis() - 3600000
    )
  )

  val sampleReviews = listOf(
    Review(
      id = "rev_1",
      customerId = "user_cust_1",
      customerName = "Vikas Singhal",
      providerId = "prov_jpr_sweet_1",
      requestId = "req_102",
      rating = 5,
      comment = "Super fresh and mouth-watering Ghewar! Delivery was on time and packaging was spotless. Highly recommended!",
      createdAt = System.currentTimeMillis() - 80000000
    ),
    Review(
      id = "rev_2",
      customerId = "user_cust_3",
      customerName = "Priyanka Joshi",
      providerId = "prov_jpr_ac_1",
      requestId = "req_old_1",
      rating = 5,
      comment = "Very polite technician. AC is chilling perfectly now after foam wash. Transparent pricing without hidden fees.",
      createdAt = System.currentTimeMillis() - 150000000
    ),
    Review(
      id = "rev_3",
      customerId = "user_cust_4",
      customerName = "Hitesh Shah",
      providerId = "prov_jmn_food_1",
      requestId = "req_old_2",
      rating = 5,
      comment = "Best Dry Fruit Kachori in all of Gujarat. Authentic taste of Jamnagar!",
      createdAt = System.currentTimeMillis() - 120000000
    )
  )

  val defaultUsers = listOf(
    UserAccount(
      id = "user_cust_1",
      name = "Vikas Singhal",
      email = "customer@ownshop.com",
      phone = "+91 97845 12000",
      role = UserRole.CUSTOMER,
      cityId = "jaipur",
      cityName = "Jaipur",
      address = "B-12, Gautam Marg, Vaishali Nagar, Jaipur"
    ),
    UserAccount(
      id = "user_prov_1",
      name = "Mahesh Sharma",
      email = "provider@ownshop.com",
      phone = "+91 98290 12345",
      role = UserRole.PROVIDER,
      cityId = "jaipur",
      cityName = "Jaipur",
      address = "42, Queens Road, Vaishali Nagar, Jaipur",
      providerId = "prov_jpr_ac_1"
    ),
    UserAccount(
      id = "user_admin_1",
      name = "OwnShop Administrator",
      email = "admin@ownshop.com",
      phone = "+91 98299 00000",
      role = UserRole.ADMIN,
      cityId = "jaipur",
      cityName = "Jaipur",
      address = "OwnShop Headquarters, Jaipur"
    )
  )
}
