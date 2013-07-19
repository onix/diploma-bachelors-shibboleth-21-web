$().ready(function () {
    $("#officeCredentials").validate({
        rules: {
            officeName: {
                required: true,
                minlength: 2,
                maxlength: 200
            },

            officeAddress: {
                required: true,
                minlength: 2,
                maxlength: 500
            }
        },
        messages: {
            officeName: {
                required: "Please enter office tag name",
                minlength: "Tag must consist of at least 2 characters",
                maxlength: "Tag must consist of less than 100 characters"
            },

            officeAddress: {
                required: "Please enter office address",
                minlength: "Address must consist of at least 2 characters",
                maxlength: "Address must consist of less than 500 characters"
            }
        }
    });
});