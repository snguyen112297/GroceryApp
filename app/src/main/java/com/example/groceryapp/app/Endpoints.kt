package com.example.groceryapp.app

class Endpoints{
    companion object{

        const val URL_LOGIN = "auth/login"
        const val URL_REGISTER = "auth/register"
        const val URL_CATEGORY = "category"
        const val URL_SUB_CATEGORY = "subcategory"
        const val URL_PRODUCTS = "products/sub"
        const val URL_PRODUCT = "products"
        const val URL_ADDRESS = "address"
        const val URL_ORDERS = "orders"

        fun getLogin(): String{
            return Config.BASE_URL + URL_LOGIN
        }

        fun getRegister(): String{
            return Config.BASE_URL + URL_REGISTER
        }

        fun getCategory(): String{
            return Config.BASE_URL + URL_CATEGORY
        }


        fun getSubCategoryByCatId(catId: Int): String{
            return "${Config.BASE_URL + URL_SUB_CATEGORY}/$catId"
        }

        fun getProductBySubId(subId: Int): String{
            return "${Config.BASE_URL + URL_PRODUCTS}/$subId"
        }

        fun getProductByProductId(productId: String?): String{
            return "${Config.BASE_URL + URL_PRODUCT}/$productId"
        }

        fun getAddressByUserId(userId: String?): String{
            return "${Config.BASE_URL + URL_ADDRESS}/$userId"
        }

        fun getOrderByUserId(userId: String?): String{
            return "${Config.BASE_URL + URL_ORDERS}/$userId"
        }

        fun postOrders(): String{
            return Config.BASE_URL + URL_ORDERS
        }

        fun saveAddress(): String{
            return Config.BASE_URL + URL_ADDRESS
        }
    }
}