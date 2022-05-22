package gui.controllers.controller_DB

import database_functions.*
import errors.ErrorClass
import java.util.*

enum class ControllerDBInteraction {
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

        override fun addInList(values: List<String>): ErrorClass {
            return ErrorClass.NULL
        }

        override fun removeFromList(values: List<String>): ErrorClass {
            return ErrorClass.NULL
        }

        override fun editInList(defaultValues: List<String>, values: List<String>): ErrorClass {
            return ErrorClass.NULL
        }

        override fun getAllColumnValues(index: Int): List<String> {
            return emptyList()
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

        override fun addInList(values: List<String>): ErrorClass {
            return addMalfunction(values[0].lowercase(Locale.getDefault()))
        }

        override fun removeFromList(values: List<String>): ErrorClass {
            return removeMalfunction(values[0].lowercase(Locale.getDefault()))
        }

        override fun editInList(defaultValues: List<String>, values: List<String>): ErrorClass {
            return editMalfunction(defaultValues[0].lowercase(Locale.getDefault()), values[0].lowercase(Locale.getDefault()))
        }

        override fun getAllColumnValues(index: Int): List<String> {
            return getElementsByIndex(0)
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

        override fun addInList(values: List<String>): ErrorClass {
            return addAttribute(values[0].lowercase(Locale.getDefault()))
        }

        override fun removeFromList(values: List<String>): ErrorClass {
            return removeAttribute(values[0].lowercase(Locale.getDefault()))
        }

        override fun editInList(defaultValues: List<String>, values: List<String>): ErrorClass {
            return editAttribute(defaultValues[0].lowercase(Locale.getDefault()), values[0].lowercase(Locale.getDefault()))
        }

        override fun getAllColumnValues(index: Int): List<String> {
            return getElementsByIndex(1)
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

        override fun addInList(values: List<String>): ErrorClass {
            return addAvailableValue(values[0].lowercase(Locale.getDefault()), values[1].lowercase(Locale.getDefault()))
        }

        override fun removeFromList(values: List<String>): ErrorClass {
            return removeAvailableValue(values[0].lowercase(Locale.getDefault()), values[1].lowercase(Locale.getDefault()))
        }

        override fun editInList(defaultValues: List<String>, values: List<String>): ErrorClass {
            return editAvailableValue(
                defaultValues[0].lowercase(Locale.getDefault()), defaultValues[1].lowercase(Locale.getDefault()),
                values[0].lowercase(Locale.getDefault()), values[1].lowercase(Locale.getDefault())
            )
        }

        override fun getAllColumnValues(index: Int): List<String> {
            return when(index) {
                0 -> getElementsByIndex(1)
                else -> getElementsByIndex(2)
            }
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

        override fun addInList(values: List<String>): ErrorClass {
            return addNormalValue(
                values[0].lowercase(Locale.getDefault()),
                values[1].lowercase(Locale.getDefault())
            )
        }

        override fun removeFromList(values: List<String>): ErrorClass {
            return removeNormalValue(
                values[0].lowercase(Locale.getDefault()),
                values[1].lowercase(Locale.getDefault())
            )
        }

        override fun editInList(defaultValues: List<String>, values: List<String>): ErrorClass {
            return editNormalValue(
                defaultValues[0].lowercase(Locale.getDefault()), defaultValues[1].lowercase(Locale.getDefault()),
                values[0].lowercase(Locale.getDefault()), values[1].lowercase(Locale.getDefault())
            )
        }

        override fun getAllColumnValues(index: Int): List<String> {
            return when(index) {
                0 -> getElementsByIndex(1)
                else -> getElementsByIndex(2)
            }
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

        override fun addInList(values: List<String>): ErrorClass {
            return addAttributePicture(
                values[0].lowercase(Locale.getDefault()),
                values[1].lowercase(Locale.getDefault())
            )
        }

        override fun removeFromList(values: List<String>): ErrorClass {
            return removeAttributeFromPicture(
                values[0].lowercase(Locale.getDefault()),
                values[1].lowercase(Locale.getDefault())
            )
        }

        override fun editInList(defaultValues: List<String>, values: List<String>): ErrorClass {
            return editAttributePicture(
                defaultValues[0].lowercase(Locale.getDefault()).lowercase(Locale.getDefault()),
                defaultValues[1].lowercase(Locale.getDefault()).lowercase(Locale.getDefault()),
                values[0].lowercase(Locale.getDefault()),
                values[1].lowercase(Locale.getDefault())
            )
        }

        override fun getAllColumnValues(index: Int): List<String> {
            return when(index) {
                0 -> getElementsByIndex(0)
                else -> getElementsByIndex(1)
            }
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

        override fun addInList(values: List<String>): ErrorClass {
            return addValuesByMalfunction(
                values[0].lowercase(Locale.getDefault()),
                values[1].lowercase(Locale.getDefault()), values[2].lowercase(Locale.getDefault())
            )
        }

        override fun removeFromList(values: List<String>): ErrorClass {
            return removeValueFromValuesByMalfunction(
                values[0].lowercase(Locale.getDefault()),
                values[1].lowercase(Locale.getDefault()), values[2].lowercase(Locale.getDefault())
            )
        }

        override fun editInList(defaultValues: List<String>, values: List<String>): ErrorClass {
            return editValuesByMalfunction(
                defaultValues[0].lowercase(Locale.getDefault()), defaultValues[1].lowercase(Locale.getDefault()),
                defaultValues[2].lowercase(Locale.getDefault()),
                values[0].lowercase(Locale.getDefault()), values[1].lowercase(Locale.getDefault()),
                values[2].lowercase(Locale.getDefault())
            )
        }

        override fun getAllColumnValues(index: Int): List<String> {
            return getElementsByIndex(index)
        }
    };

    abstract fun getList(): List<ControllerDBCrutch>
    abstract fun getNamesOfFields(): List<String>
    abstract fun getNumberOfFields(): Int
    abstract fun getWeightsOfFields(): List<Double>
    abstract fun addInList(values: List<String>): ErrorClass
    abstract fun removeFromList(values: List<String>): ErrorClass
    abstract fun editInList(defaultValues: List<String>, values: List<String>): ErrorClass

    abstract fun getAllColumnValues(index: Int): List<String>
}