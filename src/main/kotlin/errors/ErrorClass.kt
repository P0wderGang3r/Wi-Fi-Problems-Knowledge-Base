package errors

import errors.error_classes.IErrorClass
import errors.error_classes.default_error_classes.AddDefaultErrorClass
import errors.error_classes.default_error_classes.EditDefaultErrorClass
import errors.error_classes.default_error_classes.InitDefaultErrorClass
import errors.error_classes.default_error_classes.RemoveDefaultErrorClass

enum class ErrorClass {
    NULL {
        override fun getErrorHandler(): IErrorClass? {
            return null
        }
    },


    ADD_DEFAULT {
        private val errorHandler: IErrorClass = AddDefaultErrorClass()

        override fun getErrorHandler(): IErrorClass {
            return errorHandler
        }
    },


    EDIT_DEFAULT {
        private val errorHandler: IErrorClass = EditDefaultErrorClass()

        override fun getErrorHandler(): IErrorClass {
            return errorHandler
        }
    },


    REMOVE_DEFAULT {
        private val errorHandler: IErrorClass = RemoveDefaultErrorClass()

        override fun getErrorHandler(): IErrorClass {
            return errorHandler
        }
    },


    INIT_DEFAULT {
        private val errorHandler: IErrorClass = InitDefaultErrorClass()

        override fun getErrorHandler(): IErrorClass {
            return errorHandler
        }
    };

    abstract fun getErrorHandler(): IErrorClass?
}