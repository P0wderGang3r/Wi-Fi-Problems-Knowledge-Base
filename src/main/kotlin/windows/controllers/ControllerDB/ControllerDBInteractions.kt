package windows.controllers.ControllerDB

import malfunctions
import attributes
import attributePictures
import database_functions.*
import valuesByMalfunctions

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

        override fun getList(): List<ControllerCrutch> {
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

        override fun getList(): List<ControllerCrutch> {
            val result: ArrayList<ControllerCrutch> = ArrayList()

            for (malfunction in malfunctions) {
                result.add(ControllerCrutch(malfunction.name, "", ""))
            }

            return result
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
            addMalfunction(values[0])
        }

        override fun removeFromList(values: List<String>) {
            removeMalfunction(values[0])
        }

        override fun editInList(defaultValues: List<String>, values: List<String>) {
            editMalfunction(defaultValues[0], values[0])
        }
    },


    ATTRIBUTE {

        private val namesOfFields = listOf("Признаки", "Error", "Error")
        private val weightsOfFields = listOf(1.0, 0.0, 0.0)

        override fun getList(): List<ControllerCrutch> {
            val result: ArrayList<ControllerCrutch> = ArrayList()

            for (attribute in attributes) {
                result.add(ControllerCrutch(attribute.name, "", ""))
            }

            return result
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
            addAttribute(values[0])
        }

        override fun removeFromList(values: List<String>) {
            removeAttribute(values[0])
        }

        override fun editInList(defaultValues: List<String>, values: List<String>) {
            editAttribute(defaultValues[0], values[0])
        }
    },


    ATTRIBUTE_AVAILABLE_VALUES {

        private val namesOfFields = listOf("Признаки", "Возможные значения", "Error")
        private val weightsOfFields = listOf(1.0, 0.5, 0.0)

        override fun getList(): List<ControllerCrutch> {
            val result: ArrayList<ControllerCrutch> = ArrayList()

            for (attribute in attributes) {
                for (value in attribute.availableValues)
                    result.add(ControllerCrutch(attribute.name, value, ""))
            }

            return result
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
            addAvailableValue(values[0], values[1])
        }

        override fun removeFromList(values: List<String>) {
            removeAvailableValue(values[0], values[1])
        }

        override fun editInList(defaultValues: List<String>, values: List<String>) {
            editAvailableValue(defaultValues[0], defaultValues[1], values[0], values[1])
        }
    },


    ATTRIBUTE_NORMAL_VALUES {

        private val namesOfFields = listOf("Признаки", "Нормальные значения", "Error")
        private val weightsOfFields = listOf(1.0, 0.5, 0.0)

        override fun getList(): List<ControllerCrutch> {
            val result: ArrayList<ControllerCrutch> = ArrayList()

            for (attribute in attributes) {
                for (value in attribute.normalValues)
                    result.add(ControllerCrutch(attribute.name, value, ""))
            }

            return result
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
            addNormalValue(values[0], values[1])
        }

        override fun removeFromList(values: List<String>) {
            removeNormalValue(values[0], values[1])
        }

        override fun editInList(defaultValues: List<String>, values: List<String>) {
            editNormalValue(defaultValues[0], defaultValues[1], values[0], values[1])
        }
    },


    ATTRIBUTE_PICTURE {

        private val namesOfFields = listOf("Неисправности", "Признаки", "Error")
        private val weightsOfFields = listOf(1.0, 1.0, 0.0)

        override fun getList(): List<ControllerCrutch> {
            val result: ArrayList<ControllerCrutch> = ArrayList()

            for (picture in attributePictures) {
                for (attribute in picture.attributes)
                    result.add(ControllerCrutch(picture.malfunction.name, attribute.name, ""))
            }

            return result
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
            addAttributePicture(values[0], values[1])
        }

        override fun removeFromList(values: List<String>) {
            removeAttributeFromPicture(values[0], values[1])
        }

        override fun editInList(defaultValues: List<String>, values: List<String>) {
            TODO("Not yet implemented")
        }
    },


    VALUES_BY_MALFUNCTION {

        private val namesOfFields = listOf("Неисправности", "Признаки", "Значения")
        private val weightsOfFields = listOf(1.0, 1.0, 0.5)

        override fun getList(): List<ControllerCrutch> {
            val result: ArrayList<ControllerCrutch> = ArrayList()

            for (valuesByMalfunction in valuesByMalfunctions) {
                for (value in valuesByMalfunction.values)
                    result.add(
                        ControllerCrutch(
                            valuesByMalfunction.malfunction.name,
                            valuesByMalfunction.attribute.name,
                            value
                        )
                    )
            }

            return result
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
            addValuesByMalfunction(values[0], values[1], values[2])
        }

        override fun removeFromList(values: List<String>) {
            removeValueFromValuesByMalfunction(values[0], values[1], values[2])
        }

        override fun editInList(defaultValues: List<String>, values: List<String>) {
            TODO("Not yet implemented")
        }
    };

    abstract fun getList(): List<ControllerCrutch>
    abstract fun getNamesOfFields(): List<String>
    abstract fun getNumberOfFields(): Int
    abstract fun getWeightsOfFields(): List<Double>
    abstract fun addInList(values: List<String>)
    abstract fun removeFromList(values: List<String>)
    abstract fun editInList(defaultValues: List<String>, values: List<String>)
}