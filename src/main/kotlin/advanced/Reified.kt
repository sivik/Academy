package advanced

// https://www.baeldung.com/kotlin/reified-functions
// https://stackoverflow.com/questions/45949584/how-does-the-reified-keyword-in-kotlin-work


fun <T> myGenericFun(c: Class<T>){}
inline fun <reified T> myGenericFun2(c: Class<T>){}

//fun <T> String.toKotlinObject():T{
    //val mapper = jacksonObjectMapper()
    //return mapper.readValue(this, T::class.java)
//}