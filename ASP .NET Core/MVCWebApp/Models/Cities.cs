using System.ComponentModel.DataAnnotations;

namespace MVCWebApp.Models
{
    public class Cities
    {
        [Key]
        public int CityId { get; set; }

        [Required]
        [StringLength(255)]
        public string CityName { get; set; }
    }
}
