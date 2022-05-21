package gui.controllers.controller_DB

import database_functions.*
import java.util.*

enum class ControllerDBInteractions {
    NULL {
        override fun getWeightsOfFields(): List<Double> {
            return emptyList()
        }

        override fun getNumberOfFields(): Int {
            return 0
        }

        override fun getNamesOfFields(): List<String> {
            return emptyList()
        }

        override fun getList(): List<ControllerDBCrutch> {
            return emptyList()
        }

        override fun addInList(values: List<String>) {

        }

        override fun removeFromList(values: List<String>) {

        }

        override fun editInList(defaultValues: List<String>, values: List<String>) {

        }
    },


    MALFUNCTION {

        private val namesOfFields = listOf("Неисправности", "Error", "Error")
        private val weightsOfFields = listOf(1.0, 0.0, 0.0)

        override fun getList(): List<ControllerDBCrutch> {
            return getMalfunctions()
        }

        override fun getNamesOfFields(): List<String> {
            return namesOfFields
        }

        override fun getNumberOfFields(): Int {
            return 1
        }

        override fun getWeightsOfFields(): List<Double> {
            return weightsOfFields
        }

        override fun addInList(values: List<String>) {
            addMalfunction(values[0].lowercase(Locale.getDefault()))
        }

        override fun removeFromList(values: List<String>) {
            removeMalfunction(values[0].lowercase(Locale.getDefault()))
        }

        override fun editInList(defaultValues: List<String>, values: List<String>) {
            editMalfunction(defaultValues[0].lowercase(Locale.getDefault()), values[0].lowercase(Locale.getDefault()))
        }
    },


    ATTRIBUTE {

        private val namesOfFields = listOf("Признаки", "Error", "Error")
        private val weightsOfFields = listOf(1.0, 0.0, 0.0)

        override fun getList(): List<ControllerDBCrutch> {
            return getAttributes()
        }

        override fun getNamesOfFields(): List<String> {
            return namesOfFields
        }

        override fun getNumberOfFields(): Int {
            return 1
        }

        override fun getWeightsOfFields(): List<Double> {
            return weightsOfFields
        }

        override fun addInList(values: List<String>) {
            addAttribute(values[0].lowercase(Locale.getDefault()))
        }

        override fun removeFromList(values: List<String>) {
            removeAttribute(values[0].lowercase(Locale.getDefault()))
        }

        override fun editInList(defaultValues: List<String>, values: List<String>) {
            editAttribute(defaultValues[0].lowercase(Locale.getDefault()), values[0].lowercase(Locale.getDefault()))
        }
    },


    ATTRIBUTE_AVAILABLE_VALUES {

        private val namesOfFields = listOf("Признаки", "Возможные значения", "Error")
        private val weightsOfFields = listOf(1.0, 0.5, 0.0)

        override fun getList(): List<ControllerDBCrutch> {
            return getAvailableValues()
        }

        override fun getNamesOfFields(): List<String> {
            return namesOfFields
        }

        override fun getNumberOfFields(): Int {
            return 2
        }

        override fun getWeightsOfFields(): List<Double> {
            return weightsOfFields
        }

        override fun addInList(values: List<String>) {
            addAvailableValue(values[0].lowercase(Locale.getDefault()), values[1].lowercase(Locale.getDefault()))
        }

        override fun removeFromList(values: List<String>) {
            removeAvailableValue(values[0].lowercase(Locale.getDefault()), values[1].lowercase(Locale.getDefault()))
        }

        override fun editInList(defaultValues: List<String>, values: List<String>) {
            editAvailableValue(
                defaultValues[0].lowercase(Locale.getDefault()), defaultValues[1].lowercase(Locale.getDefault()),
                values[0].lowercase(Locale.getDefault()), values[1].lowercase(Locale.getDefault())
            )
        }
    },


    ATTRIBUTE_NORMAL_VALUES {

        private val namesOfFields = listOf("Признаки", "Нормальные значения", "Error")
        private val weightsOfFields = listOf(1.0, 0.5, 0.0)

        override fun getList(): List<ControllerDBCrutch> {
            return getNormalValues()
        }

        override fun getNamesOfFields(): List<String> {
            return namesOfFields
        }

        override fun getNumberOfFields(): Int {
            return 2
        }

        override fun getWeightsOfFields(): List<Double> {
            return weightsOfFields
        }

        override fun addInList(values: List<String>) {
            addNormalValue(
                values[0].lowercase(Locale.getDefault()),
                values[1].lowercase(Locale.getDefault())
            )
        }

        override fun removeFromList(values: List<String>) {
            removeNormalValue(
                values[0].lowercase(Locale.getDefault()),
                values[1].lowercase(Locale.getDefault())
            )
        }

        override fun editInList(defaultValues: List<String>, values: List<String>) {
            editNormalValue(
                defaultValues[0].lowercase(Locale.getDefault()), defaultValues[1].lowercase(Locale.getDefault()),
                values[0].lowercase(Locale.getDefault()), values[1].lowercase(Locale.getDefault())
            )
        }
    },


    ATTRIBUTE_PICTURE {

        private val namesOfFields = listOf("Неисправности", "Признаки", "Error")
        private val weightsOfFields = listOf(1.0, 1.0, 0.0)

        override fun getList(): List<ControllerDBCrutch> {
            return getAttributePicture()
        }

        override fun getNamesOfFields(): List<String> {
            return namesOfFields
        }

        override fun getNumberOfFields(): Int {
            return 2
        }

        override fun getWeightsOfFields(): List<Double> {
            return weightsOfFields
        }

        override fun addInList(values: List<String>) {
            addAttributePicture(
                values[0].lowercase(Locale.getDefault()),
                values[1].lowercase(Locale.getDefault())
            )
        }

        override fun removeFromList(values: List<String>) {
            removeAttributeFromPicture(
                values[0].lowercase(Locale.getDefault()),
                values[1].lowercase(Locale.getDefault())
            )
        }

        override fun editInList(defaultValues: List<String>, values: List<String>) {
            editAttributePicture(
                defaultValues[0].lowercase(Locale.getDefault()).lowercase(Locale.getDefault()),
                defaultValues[1].lowercase(Locale.getDefault()).lowercase(Locale.getDefault()),
                values[0].lowercase(Locale.getDefault()),
                values[1].lowercase(Locale.getDefault())
            )
        }
    },


    VALUES_BY_MALFUNCTION {

        private val namesOfFields = listOf("Неисправности", "Признаки", "Значения")
        private val weightsOfFields = listOf(1.0, 1.0, 0.6)

        override fun getList(): List<ControllerDBCrutch> {
            return getValuesByMalfunctions()
        }

        override fun getNamesOfFields(): List<String> {
            return namesOfFields
        }

        override fun getNumberOfFields(): Int {
            return 3
        }

        override fun getWeightsOfFields(): List<Double> {
            return weightsOfFields
        }

        override fun addInList(values: List<String>) {
            addValuesByMalfunction(
                values[0].lowercase(Locale.getDefault()),
                values[1].lowercase(Locale.getDefault()), values[2].lowercase(Locale.getDefault())
            )
        }

        override fun removeFromList(values: List<String>) {
            removeValueFromValuesByMalfunction(
                values[0].lowercase(Locale.getDefault()),
                values[1].lowercase(Locale.getDefault()), values[2].lowercase(Locale.getDefault())
            )
        }

        override fun editInList(defaultValues: List<String>, values: List<String>) {
            editValuesByMalfunction(
                defaultValues[0].lowercase(Locale.getDefault()), defaultValues[1].lowercase(Locale.getDefault()),
                defaultValues[2].lowercase(Locale.getDefault()),
                values[0].lowercase(Locale.getDefault()), values[1].lowercase(Locale.getDefault()),
                values[2].lowercase(Locale.getDefault())
            )
        }
    };

    abstract fun getList(): List<ControllerDBCrutch>
    abstract fun getNamesOfFields(): List<String>
    abstract fun getNumberOfFields(): Int
    abstract fun getWeightsOfFields(): List<Double>
    abstract fun addInList(values: List<String>)
    abstract fun removeFromList(values: List<String>)
    abstract fun editInList(defaultValues: List<String>, values: List<String>)
}