using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using MVCWebApp.Data;
using MVCWebApp.Models;

namespace MVCWebApp.Controllers
{
    public class UserController : Controller
    {
        private readonly ApplicationDbContext _context;

        public UserController(ApplicationDbContext context)
        {
            _context = context;
        }

        public IActionResult Index(string search)
        {
            var query = _context.UserInfo.AsQueryable();

            if (!string.IsNullOrEmpty(search))
            {
                query = query.Where(user =>
                    user.Name.Contains(search) ||
                    user.MobileNumber.Contains(search) ||
                    user.EmailID.Contains(search) ||
                    user.City.Contains(search));
            }

            ViewBag.Search = search;
            var users = query.ToList();
            return View(users);
        }


        public IActionResult Create()
        {
            ViewBag.Cities = _context.Cities.ToList();
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public IActionResult Create(UserInfo userInfo)
        {
            if (ModelState.IsValid)
            {
                _context.UserInfo.Add(userInfo);
                _context.SaveChanges();
                return RedirectToAction(nameof(Index));
            }

            ViewBag.Cities = _context.Cities.ToList(); 
            return View(userInfo);
        }

        // GET: User/Edit/5
        public IActionResult Edit(int id)
        {
            var userInfo = _context.UserInfo.Find(id);
            if (userInfo == null)
            {
                return NotFound();
            }
            return View(userInfo);
        }

        // POST: User/Edit/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public IActionResult Edit(int id, UserInfo userInfo)
        {
            if (id != userInfo.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                _context.Entry(userInfo).State = EntityState.Modified;
                _context.SaveChanges();
                return RedirectToAction(nameof(Index));
            }
            return View(userInfo);
        }

        // GET: User/Delete/5
        public IActionResult Delete(int Id)
        {
            var userInfo = _context.UserInfo.Find(Id);
            if (userInfo == null)
            {
                return NotFound();
            }
            return View(userInfo);
        }

        // POST: User/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public IActionResult DeleteConfirmed(int Id)
        {
            var userInfo = _context.UserInfo.Find(Id);
            _context.UserInfo.Remove(userInfo);
            _context.SaveChanges();
            return RedirectToAction("Index");
        }

        public IActionResult Login()
        {
            return View();
        }

        [HttpPost]
        public IActionResult Login(UserInfo model)
        {
            var user = _context.UserInfo.SingleOrDefault(u => u.EmailID == model.EmailID && u.Password == model.Password);

            if (user != null)
            {
                
                HttpContext.Session.SetString("UserID", user.Id.ToString());

               
                HttpContext.Session.SetInt32("SessionTimeout", (int)TimeSpan.FromMinutes(5).TotalSeconds);

               
                HttpContext.Session.SetString("LastAccessTime", DateTime.Now.ToString());

                // Redirect to a different view upon successful login
                return RedirectToAction("Index", "User"); 
            }
            else
            {
                ViewBag.ErrorMessage = "Invalid email or password.";
                return View();
            }
        }


        public IActionResult Logout()
        {
            HttpContext.Session.Clear();
            return RedirectToAction("Login");
        }



    }
}
