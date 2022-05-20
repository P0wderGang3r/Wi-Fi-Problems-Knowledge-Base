package gui.controllers.controller_DB

class ControllerDBCrutch(val firstValue: String,
                         val secondValue: String,
                         val thirdValue: String)
{
    @JvmName("getFirstValue1")
    fun getFirstValue(): String {
        return firstValue
    }

    @JvmName("getSecondValue1")
    fun getSecondValue(): String {
        return secondValue
    }

    @JvmName("getThirdValue1")
    fun getThirdValue(): String {
        return thirdValue
    }
}