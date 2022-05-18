package database_functions

import attributePictures
import attributes
import data_classes.AttributeClass
import data_classes.AttributePictureClass
import data_classes.MalfunctionClass
import data_classes.ValuesByMalfunctionClass
import malfunctions
import valuesByMalfunctions
import java.io.FileReader

/*
 * Считывание текста из файла.
 */
fun initText(fileName: String): String {
    val reader: FileReader
    var lText = ""

    try {
        reader = FileReader(fileName)
    } catch (e: java.lang.Exception) {
        return "Error"
    }

    var inp: Int = reader.read()

    while (inp != -1) {
        lText = lText.plus(inp.toChar())
        inp = reader.read()
    }
    return lText
}

fun initArrayOfText(fileName: String): ArrayList<String> {
    val text = initText(fileName)

    val lineList = text.split("\n\r", "\n", "\r") as java.util.ArrayList<String>

    lineList.remove("")

    return lineList
}

fun initInputStructure(fileName: String): ArrayList<List<String>> {
    val lineList = initArrayOfText(fileName)

    val inputStructure: ArrayList<List<String>> = ArrayList()

    for (line in lineList) {
        inputStructure.add(line.split(" : "))
    }

    return inputStructure
}

fun initMalfunctionClasses(path: String): Boolean {
    val rawMalfunctions = initInputStructure(path + "malfunctions.txt")

    for (malfunction in rawMalfunctions) {
        malfunctions.add(MalfunctionClass(Integer.parseInt(malfunction[0]), malfunction[1]))
    }

    return true
}

fun initAttributeClasses(path: String): Boolean {
    val rawAttributes = initInputStructure(path + "attributes.txt")

    for (attribute in rawAttributes) {
        attributes.add(AttributeClass(Integer.parseInt(attribute[0]), attribute[1]))
    }

    return true
}


fun initAvailableValueClasses(path: String): Boolean {
    val rawValues = initInputStructure(path + "available_values.txt")

    for (value in rawValues) {
        for (attribute in attributes) {
            if (attribute.number == Integer.parseInt(value[0])) {
                attribute.availableValues.add(value[1])
            }
        }
    }

    return true
}

fun initNormalValueClasses(path: String): Boolean {
    val rawValues = initInputStructure(path + "normal_values.txt")

    for (value in rawValues) {
        for (attribute in attributes) {
            if (attribute.number == Integer.parseInt(value[0])) {
                attribute.normalValues.add(value[1])
            }
        }
    }

    return true
}

fun initAttributePicture(path: String): Boolean {
    val rawPictures = initInputStructure(path + "attribute_picture.txt")

    for (picture in rawPictures) {
        val attributesInPicture = picture[1].split(" ; ")

        val numOfMalfunction = Integer.parseInt(picture[0])

        for (malfunction in malfunctions) {
            if (malfunction.number == numOfMalfunction) {
                attributePictures.add(AttributePictureClass(malfunction))
            }
        }

        for (attributeNum in attributesInPicture) {
            for (attribute in attributes) {
                if (attribute.number == Integer.parseInt(attributeNum)) {
                    attributePictures[attributePictures.size - 1].attributes.add(attribute)
                }
            }
        }
    }

    return true
}

fun initValuesByMalfunction(path: String): Boolean {
    val rawValues = initInputStructure(path + "values_by_malfunction.txt")

    for (valueByMalfunction in rawValues) {
        val numOfMalfunction = Integer.parseInt(valueByMalfunction[0])
        val numOfAttribute = Integer.parseInt(valueByMalfunction[1])

        for (attributePicture in attributePictures) {
            if (attributePicture.malfunction.number == numOfMalfunction) {

                for (attribute in attributePicture.attributes) {
                    if (attribute.number == numOfAttribute) {

                        valuesByMalfunctions.add(
                            ValuesByMalfunctionClass(
                                attributePicture.malfunction,
                                attribute
                            )
                        )

                        val values = valueByMalfunction[2].split(" ; ")

                        for (value in values) {
                            valuesByMalfunctions[valuesByMalfunctions.size - 1].values.add(value)
                        }

                    }
                }

            }
        }

    }

    return true
}

fun clearDataBase() {
    valuesByMalfunctions.clear()
    attributePictures.clear()
    malfunctions.clear()
    for (attribute in attributes) {
        attribute.availableValues.clear()
        attribute.normalValues.clear()
    }
    attributes.clear()
}

fun initDataBase(inpPath: String): Boolean {
    var path = inpPath

    if (path.isNotEmpty() && path[path.length - 1] != '/') {
        path += "/"
    }

    clearDataBase()

    try {
        if (!initMalfunctionClasses(path)) {
            println("Ошибка прочтения перечня неисправностей")
            return false
        }

        if (!initAttributeClasses(path)) {
            println("Ошибка прочтения перечня признаков")
            return false
        }

        if (!initAvailableValueClasses(path)) {
            println("Ошибка прочтения перечня областей допустимых значений")
            return false
        }

        if (!initNormalValueClasses(path)) {
            println("Ошибка прочтения перечня областей возможных значений")
            return false
        }

        if (!initAttributePicture(path)) {
            println("Ошибка прочтения перечня значимых признаков для неисправностей")
            return false
        }

        if (!initValuesByMalfunction(path)) {
            println("Ошибка прочтения перечня значений признаков при неисправности")
            return false
        }

    } catch (e: java.lang.Exception) {
        return false
    }

    return true
}