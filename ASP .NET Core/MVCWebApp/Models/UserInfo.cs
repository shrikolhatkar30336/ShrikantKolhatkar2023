using System.ComponentModel.DataAnnotations;

namespace MVCWebApp.Models
{
    public class UserInfo
    {
        public int Id { get; set; }

        [Required]
        [MaxLength(50)]
        public string Name { get; set; }

        [Required(ErrorMessage = "Password is required")]
        [MinLength(8)]
        [RegularExpression(@"^\S*$", ErrorMessage = "Spaces are not allowed.")]
        public string Password { get; set; }

        [Required]
        [RegularExpression(@"^\d{10}$", ErrorMessage = "Mobile number should have 10 numeric characters.")]
        public string MobileNumber { get; set; }

        [Required(ErrorMessage = "Email is required")]

        [EmailAddress(ErrorMessage = "Invalid email address.")]
        public string EmailID { get; set; }

        [MaxLength(50)]
        public string City { get; set; }
    }

}
