package database_functions

import attributePictures
import attributes
import data_classes.AttributeClass
import data_classes.AttributePictureClass
import data_classes.MalfunctionClass
import data_classes.ValuesByMalfunctionClass
import malfunctions
import valuesByMalfunctions


fun editMalfunction(malfunctionName: String, newName: String): Boolean {
    if (malfunctionName == "")
        return false

    if (newName == "")
        return false

    val malfunction = findMalfunction(malfunctionName)

    if (malfunction != null) {
        malfunction.name = newName
        return true
    }

    return false
}

fun editAttribute(attributeName: String, newName: String): Boolean {
    if (attributeName == "")
        return false

    val attribute = findAttribute(attributeName)

    if (attribute != null) {
        attribute.name = newName
        return true
    }

    return false
}

fun editAvailableValue(attributeName: String, value: String, newAttributeName: String, newValueName: String): Boolean {
    if (attributeName == "" || value == "")
        return false

    if (newAttributeName == "" || newValueName == "")
        return false

    val attribute = findAttribute(attributeName)

    if (attribute != null) {
        var isInNormalValues = false

        //Удаляем из возможных значений
        for (currentValue in attribute.availableValues) {
            if (currentValue.equals(value)) {
                attribute.availableValues.remove(currentValue)
                break
            }
        }

        //Удаляем из нормальных значений
        for (currentValue in attribute.normalValues) {
            if (currentValue.equals(value)) {
                attribute.normalValues.remove(currentValue)
                isInNormalValues = true
                break
            }
        }

        //Добавляем в возможные значения
        attribute.availableValues.add(newValueName)

        //Добавляем в нормальные значения, если в них было
        if(isInNormalValues)
            attribute.normalValues.add(newValueName)

        attribute.name = newAttributeName

        return true
    }

    return false
}

fun editNormalValue(attributeName: String, value: String, newAttributeName: String, newValueName: String): Boolean {
    if (attributeName == "" || value == "")
        return false

    if (newAttributeName == "" || newValueName == "")
        return false

    val attribute = findAttribute(attributeName)

    if (attribute != null) {
        var isInNormalValues = false

        //Удаляем из возможных значений
        for (currentValue in attribute.availableValues) {
            if (currentValue.equals(value)) {
                attribute.availableValues.remove(currentValue)
                break
            }
        }

        //Удаляем из нормальных значений
        for (currentValue in attribute.normalValues) {
            if (currentValue.equals(value)) {
                attribute.normalValues.remove(currentValue)
                isInNormalValues = true
                break
            }
        }

        //Добавляем в возможные значения
        attribute.availableValues.add(newValueName)

        //Добавляем в нормальные значения, если в них было
        if(isInNormalValues)
            attribute.normalValues.add(newValueName)

        attribute.name = newAttributeName

        return true
    }

    return false
}

fun editAttributePicture(malfunctionName: String, attributeName: String): Boolean {
    if (malfunctionName == "" ||attributeName == "")
        return false

    val malfunction = findMalfunction(malfunctionName) ?: return false
    findAttribute(attributeName) ?: return false

    var picture = findAttributePicture(malfunctionName)

    if (picture == null) {
        picture = AttributePictureClass(malfunction)
        attributePictures.add(picture)
    }

    val attribute = findAttributeInPicture(picture, attributeName)

    if (attribute == null) {
        picture.attributes.add(AttributeClass(0, attributeName))
        return true
    }

    return false
}

fun editValuesByMalfunction(malfunctionName: String, attributeName: String, value: String): Boolean {
    if (malfunctionName == "" || attributeName == "" || value == "")
        return false

    val malfunction = findMalfunction(malfunctionName) ?: return false
    val attribute = findAttribute(attributeName) ?: return false
    if (!findValue(value, attribute.availableValues))
        return false

    var valuesByMalfunction = findValuesByMalfunction(malfunctionName, attributeName)

    if (valuesByMalfunction == null) {
        valuesByMalfunction = ValuesByMalfunctionClass(malfunction, attribute)
        valuesByMalfunctions.add(valuesByMalfunction)
    }

    if (findValueInValuesByMalfunction(valuesByMalfunction.values, value) == null) {
        valuesByMalfunction.values.add(value)
        return true
    }

    return false
}
