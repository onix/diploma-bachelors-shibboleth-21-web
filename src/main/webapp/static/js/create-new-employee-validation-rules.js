jQuery.validator.addMethod("checkIsPhotoLoaded", function(value, element) {
        alert ($("#photoUploader").hasClass("fileupload-new"));
        return !$("#photoUploader").hasClass("fileupload-new");
    },
    "Need to pick a photo!"); //TODO
//jQuery.validator.classRuleSettings.checkIsPhotoLoaded = { checkIsPhotoLoaded: false };

$().ready(function () {
    $("#employeeDataForm").validate({
        rules: {
            employeeName: {
                required: true,
                minlength: 2,
                maxlength: 100
            },

            employeeSurname: {
                required: true,
                minlength: 2,
                maxlength: 100
            },

            employeeSecondName: {
                required: true,
                minlength: 2,
                maxlength: 100
            },

            employeeAddress: {
                required: true,
                minlength: 2,
                maxlength: 500
            },

            employeePhone: {
                required: true,
                minlength: 2,
                maxlength: 50
            },

            employeeEmail: {
                required: true,
                minlength: 5,
                maxlength: 100,
                email: true
            },

            photoUploader: {checkIsPhotoLoaded: true}
        },
        messages: {
            employeeName: {
                required: "Please enter employee name",
                minlength: "Name must consist of at least 2 characters",
                maxlength: "Name must consist of less than 100 characters"
            },

            employeeSurname: {
                required: "Please enter employee surname",
                minlength: "Surname must consist of at least 2 characters",
                maxlength: "Surname must consist of less than 100 characters"
            },

            employeeSecondName: {
                required: "Please enter employee second name",
                minlength: "Second name must consist of at least 2 characters",
                maxlength: "Second name must consist of less than 100 characters"
            },

            employeeAddress: {
                required: "Please enter employee address",
                minlength: "Address must consist of at least 2 characters",
                maxlength: "Address must consist of less than 500 characters"
            },

            employeePhone: {
                required: "Please enter employee phone number",
                minlength: "Phone number must consist of at least 2 characters",
                maxlength: "Phone number must consist of less than 50 characters"
            },

            emailRegistrationField: {
                required: "Please enter an email",
                email: "Please enter a valid email address",
                minlength: "Your email must consist of at least 5 characters",
                maxlength: "Your email must consist of less than 100 characters"
            }
        }
    });
});