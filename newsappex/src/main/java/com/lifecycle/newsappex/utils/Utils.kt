package com.lifecycle.newsappex.utils


//fun loadNewsFromJson( context: Context):List<NewArt>{
//    var json: String? = null
//    json = try {
//        val `is`: InputStream = context.assets.open("newsdata.json")
//        val size: Int = `is`.available()
//        val buffer = ByteArray(size)
//        `is`.read(buffer)
//        `is`.close()
//       // String(buffer, "UTF-8")//
//    } catch (ex: IOException) {
//        ex.printStackTrace()
//        return emptyList()
//    }
//    val logs = Gson().fromJson(json!!, object : TypeToken<List<NewArt?>?>() {}.getType())
//    return logs
//
//}