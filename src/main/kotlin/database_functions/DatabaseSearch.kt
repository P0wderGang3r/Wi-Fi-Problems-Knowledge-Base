package database_functions

import data_classes.AttributeClass
import data_classes.AttributePictureClass
import data_classes.MalfunctionClass
import data_classes.ValuesByMalfunctionClass
import attributePictures
import attributes
import malfunctions
import valuesByMalfunctions

fun findValue(value: String, values: ArrayList<String>): Boolean {
    for (valueInList in values) {
        if (value.equals(valueInList)) {
            return true
        }
    }

    return false
}

fun findMalfunction(name: String): MalfunctionClass? {
    for (malfunction in malfunctions) {
        if (malfunction.name.equals(name)) {
            return malfunction
        }
    }

    return null
}

fun findAttribute(name: String): AttributeClass? {
    for (attribute in attributes) {
        if (attribute.name.equals(name)) {
            return attribute
        }
    }

    return null
}

fun findAttributePicture(name: String): AttributePictureClass? {
    for (attributePicture in attributePictures) {
        if (attributePicture.malfunction.name.equals(name)) {
            return attributePicture
        }
    }

    return null
}

fun findAttributeInPicture(picture: AttributePictureClass, name: String): AttributeClass? {
    for (attribute in picture.attributes) {
        if (attribute.name.equals(name)) {
            return attribute
        }
    }

    return null
}

fun findValuesByMalfunction(malfunctionName: String, attributeName: String): ValuesByMalfunctionClass? {
    for (valueByMalfunction in valuesByMalfunctions) {
        if (valueByMalfunction.malfunction.name.equals(malfunctionName)) {
            if (valueByMalfunction.attribute.name.equals(attributeName)) {
                return valueByMalfunction
            }
        }
    }

    return null
}

fun findValueInValuesByMalfunction(values: ArrayList<String>, valueToSearch: String): String? {
    for (value in values) {
        if (value.equals(valueToSearch))
            return value
    }

    return null
}