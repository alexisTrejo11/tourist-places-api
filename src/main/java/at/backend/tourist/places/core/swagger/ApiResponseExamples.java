package at.backend.tourist.places.core.swagger;

public class ApiResponseExamples {

    public static final String USER = """
    {
        "success": true,
        "data": {
            "id": 1,
            "name": "John Doe",
            "email": "john.doe@example.com",
            "role": "USER",
            "activated": true,
            "provider": "local"
        },
        "message": "User data successfully fetched",
        "status_code": 200
    }
    """;

    public static final String USERS = """
    {
        "success": true,
        "data": [
            {
                "id": 1,
                "name": "John Doe",
                "email": "john.doe@example.com",
                "role": "USER",
                "activated": true,
                "provider": "local"
            },
            {
                "id": 2,
                "name": "Jane Smith",
                "email": "jane.smith@example.com",
                "role": "USER",
                "activated": true,
                "provider": "local"
            },
            {
                "id": 3,
                "name": "Admin User",
                "email": "admin@example.com",
                "role": "ADMIN",
                "activated": true,
                "provider": "local"
            }
        ],
        "message": "Users data successfully fetched",
        "status_code": 200
    }
    """;

    public static final String USER_CREATED = """
    {
        "success": true,
        "data": {
            "id": 10,
            "name": "Michael Johnson",
            "email": "michael.johnson@example.com",
            "role": "USER",
            "activated": false,
            "provider": "local"
        },
        "message": "User successfully created",
        "status_code": 201
    }
    """;

    public static final String USER_DELETED = """
    {
        "success": true,
        "data": null,
        "message": "User successfully deleted",
        "status_code": 204
    }
    """;

    public static final String USER_PLACE_LIST_CREATED = """
    {
      "success": true,
      "data": {
        "id": 5,
        "user_id": 1,
        "name": "Summer Vacation 2026",
        "description": "Places to visit during summer break",
        "places": []
      },
      "message": "Place List successfully created",
      "status_code": 201
    }
    """;

    public static final String USER_REVIEW_CREATED = """
    {
      "success": true,
      "data": {
        "id": 20,
        "rating": 5,
        "comment": "Incredible experience! Highly recommend visiting this place.",
        "author_id": "john.doe@example.com",
        "place_id": 1
      },
      "message": "Review successfully created",
      "status_code": 201
    }
    """;

    public static final String SUCCESS = """
        {
          "success": true,
          "data":  null,
          "message": "Success Message",
          "status_code": 200
        }
    """;

    public static final String NOT_FOUND = """
        {
          "success": false,
          "data": null,
          "message": "Not found Message",
          "status_code": 404
        }
    """;

    public static final String CREATED = """
        {
          "success": false,
          "data": {
         \s
          },
          "message": "Created Message",
          "status_code": 201
        }
   \s""";

    public static final String UNAUTHORIZED_ACCESS = """
        {
          "success": false,
          "data": null,
          "message": "Unauthorized access",
          "status_code": 401
        }
    """;

    public static final String BAD_REQUEST = """
        {
          "success": false,
          "data": null,
          "message": "Bad request, invalid parameters",
          "status_code": 400
        }
    """;

    public static final String INTERNAL_SERVER_ERROR = """
        {
          "success": false,
          "data": null,
          "message": "Internal server error, please try again later",
          "status_code": 500
        }
    """;

    public static final String FORBIDDEN = """
        {
          "success": false,
          "data": null,
          "message": "Access forbidden, insufficient permissions",
          "status_code": 403
        }
    """;

    public static final String CONFLICT = """
        {
          "success": false,
          "data": null,
          "message": "Conflict, resource already exists",
          "status_code": 409
        }
    """;

    public static final String NO_CONTENT = """
        {
          "success": true,
          "data": null,
          "message": "No content to return",
          "status_code": 204
        }
    """;

    public static final String ACCEPTED = """
        {
          "success": true,
          "data": null,
          "message": "Request accepted but not yet processed",
          "status_code": 202
        }
    """;

    public static final String NOT_MODIFIED = """
        {
          "success": true,
          "data": null,
          "message": "The resource has not been modified",
          "status_code": 304
        }
    """;

    public static final String ACTIVITY_CREATED = """
        {
          "success": true,
          "data": {
            "id": 1,
            "name": "City Tour",
            "description": "A guided tour through the city's landmarks.",
            "price": 49.99,
            "duration": "2h",
            "tourist_place_id": 101
          },
          "message": "activity successfully created",
          "status_code": 201
        }
    """;

    public static final String ACTIVITY = """
        {
          "success": true,
          "data": {
            "id": 1,
            "name": "City Tour",
            "description": "A guided tour through the city's landmarks.",
            "price": 49.99,
            "duration": "2h",
            "tourist_place_id": 101
          },
          "message": "activity data successfully fetched",
          "status_code": 200
        }
    """;

    public static final String ACTIVITIES = """
        {
          "success": true,
          "data": [
            {
              "id": 1,
              "name": "City Tour",
              "description": "A guided tour through the city's landmarks.",
              "price": 49.99,
              "duration": "2h",
              "tourist_place_id": 101
            },
            {
              "id": 2,
              "name": "Mountain Hiking",
              "description": "An adventurous hiking experience through scenic mountain trails.",
              "price": 75.50,
              "duration": "4h",
              "tourist_place_id": 102
            },
            {
              "id": 3,
              "name": "Boat Tour",
              "description": "Relaxing boat ride along the beautiful coastline.",
              "price": 35.00,
              "duration": "1.5h",
              "tourist_place_id": 103
            }
          ],
          "message": "Activities data successfully fetched",
          "status_code": 200
        }
    """;

    public static final String ACTIVITY_DELETED = """
        {
          "success": true,
          "data": null,
          "message": "activity successfully deleted",
          "status_code": 204
        }
    """;

    public static final String COUNTRY = """
    {
      "success": true,
      "data": {
        "id": 1,
        "name": "Japan",
        "capital": "Tokyo",
        "currency": "Yen",
        "language": "Japanese",
        "population": 125800000,
        "area": 377975.0,
        "continent": "ASIA",
        "flag_image": "https://example.com/flags/japan.png"
      },
      "message": "country data successfully fetched",
      "status_code": 200
    }
    """;

    public static final String COUNTRY_CREATED = """
    {
      "success": true,
      "data": {
        "id": 5,
        "name": "Brazil",
        "capital": "Brasília",
        "currency": "Real",
        "language": "Portuguese",
        "population": 215000000,
        "area": 8515767.0,
        "continent": "SOUTH_AMERICA",
        "flag_image": "https://example.com/flags/brazil.png"
      },
      "message": "country successfully created",
      "status_code": 201
    }
    """;

    public static final String COUNTRIES = """
    {
      "success": true,
      "data": [
        {
          "id": 1,
          "name": "Japan",
          "capital": "Tokyo",
          "currency": "Yen",
          "language": "Japanese",
          "population": 125800000,
          "area": 377975.0,
          "continent": "ASIA",
          "flag_image": "https://example.com/flags/japan.png"
        },
        {
          "id": 2,
          "name": "Germany",
          "capital": "Berlin",
          "currency": "Euro",
          "language": "German",
          "population": 83200000,
          "area": 357022.0,
          "continent": "EUROPE",
          "flag_image": "https://example.com/flags/germany.png"
        },
        {
          "id": 3,
          "name": "Canada",
          "capital": "Ottawa",
          "currency": "Canadian Dollar",
          "language": "English, French",
          "population": 38000000,
          "area": 9984670.0,
          "continent": "NORTH_AMERICA",
          "flag_image": "https://example.com/flags/canada.png"
        }
      ],
      "message": "Countries data successfully fetched",
      "status_code": 200
    }
    """;

    public static final String COUNTRY_DELETED = """
    {
      "success": true,
      "data": null,
      "message": "country successfully deleted",
      "status_code": 204
    }
    """;

    public static final String PLACE_LIST = """
    {
      "success": true,
      "data": {
        "id": 1,
        "user_id": 101,
        "name": "Favorite Destinations",
        "places": [
          {
            "id": 1,
            "name": "Eiffel Tower",
            "description": "Iconic tower in Paris, France.",
            "location": "Paris",
            "rating": 4.8
          },
          {
            "id": 2,
            "name": "Machu Picchu",
            "description": "Ancient Incan city in Peru.",
            "location": "Cusco",
            "rating": 4.9
          }
        ]
      },
      "message": "Place list successfully fetched",
      "status_code": 200
    }
    """;

    public static final String PLACE_LISTS = """
    {
      "success": true,
      "data": [
        {
          "id": 1,
          "user_id": 101,
          "name": "Favorite Destinations",
          "places": [
            {
              "id": 1,
              "name": "Eiffel Tower",
              "description": "Iconic tower in Paris, France.",
              "location": "Paris",
              "rating": 4.8
            },
            {
              "id": 2,
              "name": "Machu Picchu",
              "description": "Ancient Incan city in Peru.",
              "location": "Cusco",
              "rating": 4.9
            }
          ]
        },
        {
          "id": 2,
          "user_id": 102,
          "name": "Dream Vacations",
          "places": [
            {
              "id": 3,
              "name": "Grand Canyon",
              "description": "Massive canyon in Arizona, USA.",
              "location": "Arizona",
              "rating": 4.7
            },
            {
              "id": 4,
              "name": "Santorini",
              "description": "Beautiful island in Greece.",
              "location": "Greece",
              "rating": 4.9
            }
          ]
        }
      ],
      "message": "Place lists successfully fetched",
      "status_code": 200
    }
    """;

    public static final String TOURIST_PLACE = """
    {
      "success": true,
      "data": {
        "id": 1,
        "name": "Eiffel Tower",
        "description": "The iconic iron lattice tower located on the Champ de Mars in Paris, France. Built in 1889, it has become a global cultural icon and one of the most recognizable structures in the world.",
        "location": "Champ de Mars, 5 Avenue Anatole France, 75007 Paris, France",
        "rating": 4.8,
        "opening_hours": "9:30 AM - 11:45 PM",
        "price_range": "€17 - €26",
        "country_id": 1,
        "category_id": 2,
        "image_url": "https://example.com/images/eiffel-tower.jpg"
      },
      "message": "Tourist Place data successfully fetched",
      "status_code": 200
    }
    """;

    public static final String TOURIST_PLACE_CREATED = """
    {
      "success": true,
      "data": {
        "id": 15,
        "name": "Statue of Liberty",
        "description": "A colossal neoclassical sculpture on Liberty Island in New York Harbor. A gift from France to the United States, dedicated in 1886.",
        "location": "Liberty Island, New York, NY 10004, USA",
        "rating": 4.7,
        "opening_hours": "9:00 AM - 5:00 PM",
        "price_range": "$24 - $24",
        "country_id": 5,
        "category_id": 2,
        "image_url": "https://example.com/images/statue-of-liberty.jpg"
      },
      "message": "Tourist Place successfully created",
      "status_code": 201
    }
    """;

    public static final String TOURIST_PLACES = """
    {
      "success": true,
      "data": [
        {
          "id": 1,
          "name": "Eiffel Tower",
          "description": "The iconic iron lattice tower in Paris, France.",
          "location": "Champ de Mars, Paris, France",
          "rating": 4.8,
          "opening_hours": "9:30 AM - 11:45 PM",
          "price_range": "€17 - €26",
          "country_id": 1,
          "category_id": 2,
          "image_url": "https://example.com/images/eiffel-tower.jpg"
        },
        {
          "id": 2,
          "name": "Machu Picchu",
          "description": "Ancient Incan citadel set high in the Andes Mountains in Peru.",
          "location": "Cusco Region, Peru",
          "rating": 4.9,
          "opening_hours": "6:00 AM - 5:30 PM",
          "price_range": "$45 - $62",
          "country_id": 8,
          "category_id": 1,
          "image_url": "https://example.com/images/machu-picchu.jpg"
        },
        {
          "id": 3,
          "name": "Great Wall of China",
          "description": "Ancient series of fortifications built across northern China.",
          "location": "Huairou District, Beijing, China",
          "rating": 4.7,
          "opening_hours": "7:00 AM - 6:00 PM",
          "price_range": "¥40 - ¥45",
          "country_id": 3,
          "category_id": 1,
          "image_url": "https://example.com/images/great-wall.jpg"
        }
      ],
      "message": "Tourist Places data successfully fetched",
      "status_code": 200
    }
    """;

    public static final String TOURIST_PLACE_DELETED = """
    {
      "success": true,
      "data": null,
      "message": "Tourist Place successfully deleted",
      "status_code": 204
    }
    """;

    public static final String PLACE_CATEGORY = """
    {
      "success": true,
      "data": {
        "id": 1,
        "name": "Historical Sites",
        "description": "Places of historical significance and cultural heritage"
      },
      "message": "Place Category data successfully fetched",
      "status_code": 200
    }
    """;

    public static final String PLACE_CATEGORY_CREATED = """
    {
      "success": true,
      "data": {
        "id": 8,
        "name": "Adventure Parks",
        "description": "Outdoor recreational areas offering adventure activities"
      },
      "message": "Place Category successfully created",
      "status_code": 201
    }
    """;

    public static final String PLACE_CATEGORIES = """
    {
      "success": true,
      "data": [
        {
          "id": 1,
          "name": "Historical Sites",
          "description": "Places of historical significance and cultural heritage"
        },
        {
          "id": 2,
          "name": "Monuments",
          "description": "Famous monuments and architectural landmarks"
        },
        {
          "id": 3,
          "name": "Natural Wonders",
          "description": "Beautiful natural landscapes and formations"
        },
        {
          "id": 4,
          "name": "Museums",
          "description": "Art, history, and science museums"
        }
      ],
      "message": "Place Categories data successfully fetched",
      "status_code": 200
    }
    """;

    public static final String PLACE_CATEGORY_DELETED = """
    {
      "success": true,
      "data": null,
      "message": "Place Category successfully deleted",
      "status_code": 204
    }
    """;

    public static final String PLACE_LIST_ITEM = """
    {
      "success": true,
      "data": {
        "id": 1,
        "user_id": 101,
        "name": "European Bucket List",
        "description": "Must-visit places in Europe",
        "places": [
          {
            "id": 1,
            "name": "Eiffel Tower",
            "description": "Iconic tower in Paris, France.",
            "location": "Paris, France",
            "rating": 4.8
          },
          {
            "id": 5,
            "name": "Colosseum",
            "description": "Ancient amphitheater in Rome, Italy.",
            "location": "Rome, Italy",
            "rating": 4.9
          }
        ]
      },
      "message": "Place List data successfully fetched",
      "status_code": 200
    }
    """;

    public static final String PLACE_LIST_DELETED = """
    {
      "success": true,
      "data": null,
      "message": "Place List successfully deleted",
      "status_code": 204
    }
    """;

    public static final String REVIEW = """
    {
      "success": true,
      "data": {
        "id": 1,
        "rating": 5,
        "comment": "Absolutely breathtaking! The Eiffel Tower exceeded all my expectations. A must-visit landmark in Paris.",
        "author_id": "john.doe@email.com",
        "place_id": 1
      },
      "message": "Review data successfully fetched",
      "status_code": 200
    }
    """;

    public static final String REVIEW_CREATED = """
    {
      "success": true,
      "data": {
        "id": 15,
        "rating": 4,
        "comment": "Amazing historical site with stunning architecture. The tour was very informative and well-organized.",
        "author_id": "jane.smith@email.com",
        "place_id": 5
      },
      "message": "Review successfully created",
      "status_code": 201
    }
    """;

    public static final String REVIEWS = """
    {
      "success": true,
      "data": [
        {
          "id": 1,
          "rating": 5,
          "comment": "Absolutely breathtaking! The Eiffel Tower exceeded all my expectations.",
          "author_id": "john.doe@email.com",
          "place_id": 1
        },
        {
          "id": 2,
          "rating": 4,
          "comment": "Beautiful place with rich history. Definitely worth visiting!",
          "author_id": "jane.smith@email.com",
          "place_id": 2
        },
        {
          "id": 3,
          "rating": 5,
          "comment": "Once in a lifetime experience! The views are absolutely spectacular.",
          "author_id": "mike.wilson@email.com",
          "place_id": 3
        }
      ],
      "message": "Reviews data successfully fetched",
      "status_code": 200
    }
    """;

    public static final String REVIEW_DELETED = """
    {
      "success": true,
      "data": null,
      "message": "review successfully deleted",
      "status_code": 204
    }
    """;

    public static final String SIGNUP_SUCCESS = """
    {
        "success": true,
        "message": "An Email will be sent to the email provided. Use that token to activate your account.",
        "status_code": 201
    }
    """;

    public static final String LOGIN_SUCCESS = """
    {
        "success": true,
        "data": {
            "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
            "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
        },
        "message": "User logged in successfully",
        "status_code": 200
    }
    """;

    public static final String LOGIN_FAILURE = """
    {
        "success": false,
        "message": "Invalid credentials",
        "status_code": 401
    }
    """;

}
