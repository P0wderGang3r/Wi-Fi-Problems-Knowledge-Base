package database_functions

import attributePictures
import attributes
import data_classes.AttributeClass
import data_classes.AttributePictureClass
import data_classes.MalfunctionClass
import data_classes.ValuesByMalfunctionClass
import malfunctions
import valuesByMalfunctions


fun addMalfunction(malfunctionName: String): Boolean {
    if (malfunctionName == "")
        return false

    val malfunction = findMalfunction(malfunctionName)

    if (malfunction == null) {
        malfunctions.add(MalfunctionClass(0, malfunctionName))
        return true
    }

    return false
}

fun addAttribute(attributeName: String): Boolean {
    if (attributeName == "")
        return false

    val attribute = findAttribute(attributeName)

    if (attribute == null) {
        attributes.add(AttributeClass(0, attributeName))
        return true
    }

    return false
}

fun addAvailableValue(attributeName: String, value: String): Boolean {
    if (attributeName == "" || value == "")
        return false

    val attribute = findAttribute(attributeName)

    if (attribute != null) {
        if (findValue(value, attribute.availableValues)) {
            return false
        }

        attribute.availableValues.add(value)
        return true
    }

    return false
}

fun addNormalValue(attributeName: String, value: String): Boolean {
    if (attributeName == "" || value == "")
        return false

    val attribute = findAttribute(attributeName)

    if (attribute != null) {
        if (!findValue(value, attribute.availableValues))
            return false

        if (findValue(value, attribute.normalValues))
            return false


        attribute.normalValues.add(value)
        return true
    }

    return false
}

fun addAttributePicture(malfunctionName: String, attributeName: String): Boolean {
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

fun addValuesByMalfunction(malfunctionName: String, attributeName: String, value: String): Boolean {
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
