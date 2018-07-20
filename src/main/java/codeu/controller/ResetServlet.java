package codeu.controller;

import codeu.model.store.basic.UserStore;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResetServlet extends HttpServlet {

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /**
   * Set up state for handling registration-related requests. This method is only called when
   * running in a server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * This function fires when a user requests the /reset URL. It forwards the request to
   * reset.jsp.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {
    String requestUrl = request.getRequestURI();
    Boolean isReset = !requestUrl.contains("resetQuestion");
    request.setAttribute("isReset", isReset.toString());
    request.getRequestDispatcher("/WEB-INF/view/reset.jsp").forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {
    String username = request.getParameter("username");

    if(username != null) {
      if (!userStore.isUserRegistered(username)) {
        request.setAttribute("error", "Username does not exist.");
        request.setAttribute("isReset", "false");
        request.getRequestDispatcher("/WEB-INF/view/reset.jsp").forward(request, response);
        return;
      }

      request.setAttribute("isReset", "true");
      request.setAttribute("username", username);
      request.getRequestDispatcher("/WEB-INF/view/reset.jsp").forward(request, response);
    }
  }
}