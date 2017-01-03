package gkl.coursetracker.errors;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by shane on 21/09/2016.
 */
public class ErrorsServlet extends HttpServlet {

    final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = (String) req.getAttribute("javax.servlet.error.message");
        Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");

        resp.setStatus(statusCode);

        Error err = new Error();
        err.code = statusCode;
        err.message = message;

        String jsonVal = mapper.writeValueAsString(err);

        resp.setContentType(MediaType.APPLICATION_JSON + ";" +
                javax.ws.rs.core.MediaType.CHARSET_PARAMETER + "=UTF-8");
        resp.getWriter().write(jsonVal);
    }

    public class Error {
        int code;
        String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}