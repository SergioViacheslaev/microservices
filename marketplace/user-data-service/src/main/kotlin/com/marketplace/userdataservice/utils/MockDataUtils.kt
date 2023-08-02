package com.marketplace.userdataservice.utils

import com.marketplace.userdataservice.model.dto.*


fun getDummyUserDataResponseDto() = RandomUserDataResponseDto(
    7486,
    "9d0a0f6b-e8eb-4391-933c-5ad3e0758223",
    "VLTPKvs5hW",
    "Alberta",
    "Goyette",
    "alberta.goyette",
    "alberta.goyette@email.com",
    "https://robohash.org/voluptatibusvitaeporro.png?size=300x300&set=set1",
    "Male",
    "+94 701-324-6949 x6709",
    "702797432",
    "1960-08-18",
    Employment(title = "IT Assistant", keySkill = "Communication"),
    Address(
        "South Elsa",
        "Hoeger Island",
        "2670 Feeney Glen",
        "20249-4864",
        "Pennsylvania",
        "United States",
        Coordinates(lat = 10.07741029764125, lng = 57.86074909935573)
    ),
    CreditCard(ccNumber = "4772754557874"),
    Subscription(
        "Platinum",
        "Pending",
        "Paypal",
        term = "Full subscription"
    )
)
