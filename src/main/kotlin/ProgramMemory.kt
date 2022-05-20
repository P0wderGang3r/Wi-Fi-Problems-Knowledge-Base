import data_classes.AttributeClass
import data_classes.AttributePictureClass
import data_classes.DiagnosticsClass
import data_classes.MalfunctionClass

val malfunctions: ArrayList<MalfunctionClass> = ArrayList()
    get() {
        return field
    }

val attributes: ArrayList<AttributeClass> = ArrayList()
    get() {
        return field
    }

val attributePictures: ArrayList<AttributePictureClass> = ArrayList()
    get() {
        return field
    }

val diagnostics: ArrayList<DiagnosticsClass> = ArrayList()