$(function () {
    $("#card-form").validate({
        rules: {
            level: {
                required: true,
                number: true
            },
            health: {
                required: true,
                number: true
            },
            attack: {
                required: true,
                number: true
            },
            range: {
                required: true,
                number: true
            },
            description: {
                required: true
            }
        },
        messages: {
            description: "Please enter a description",
            username: {
                required: "Please enter a username",
                minlength: "Your username must consist of at least 2 characters"
            },
            password: {
                required: "Please provide a password",
                minlength: "Your password must be at least 5 characters long"
            },
            confirm_password: {
                required: "Please provide a password",
                minlength: "Your password must be at least 5 characters long",
                equalTo: "Please enter the same password as above"
            },
            email: "Please enter a valid email address"
        }
    });
});