package basics

enum class MyEnum {
    FIRST,
    SECOND,
    THIRD
}

class FailureClass{
    private enum class MyEpicEnum{
        FIVE,
        SIX,
        SEVEN
    }
}

sealed class Response{
    data class Success(val data: Int): Response()
    object Loading: Response()
    data class Error(val message: String): Response()
}

//Enumy są używane głównie jako stałe, które odnoszą się do siebie nawzajem.
//Saled class są podobne do enumów, ale pozwalają na większe możliwości dostosowania.