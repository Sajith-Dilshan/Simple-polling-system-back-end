package lk.sajith.dep8.polling.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import lk.sajith.dep8.polling.dto.PollDTO;
import lk.sajith.dep8.polling.service.ServiceFactory;
import lk.sajith.dep8.polling.service.custom.PollService;
import lk.sajith.dep8.polling.util.HttpServlet2;
import lk.sajith.dep8.polling.util.ResponseStatusException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebServlet(name = "PollServlet", urlPatterns = "/api/v1/polls/*")
public class PollServlet extends HttpServlet2 {

    private int getPollId(HttpServletRequest req){
        if (req.getPathInfo() == null) throw new ResponseStatusException(404, "Invalid end point");
        Matcher matcher = Pattern.compile("^/(\\d+)/?$").matcher(req.getPathInfo());
        if (!matcher.find()) throw new ResponseStatusException(404, "Invalid poll id");
        return Integer.parseInt(matcher.group(1));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PollService pollService = ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.POLL);
        Jsonb jsonb = JsonbBuilder.create();

        if (req.getPathInfo() == null || req.getPathInfo().equals("/")){
            System.out.println("get all polls");
            List<PollDTO> pollDTOS = pollService.listAllPolls();
            resp.setContentType("application/json");
            jsonb.toJson(pollDTOS, resp.getWriter());
        }else{
            int pollId = getPollId(req);
            PollDTO pollDTO = pollService.getPoll(pollId);
            resp.setContentType("application/json");
            jsonb.toJson(pollDTO, resp.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* Validate the URL */
        if (req.getPathInfo() != null && !req.getPathInfo().equals("/")) {
            throw new ResponseStatusException(404, "Invalid end point");
        }
        /* Validate the content type */
        if (req.getContentType() == null || !req.getContentType().toLowerCase()
                .startsWith("application/json")){
            throw new ResponseStatusException(415, "Invalid content type");
        }

        try{
            /* Convert json -> PollDTO */
            Jsonb jsonb = JsonbBuilder.create();
            PollDTO pollDTO = jsonb.fromJson(req.getReader(), PollDTO.class);

            /* Validate pollDTO */
            if (pollDTO.getId() != null){
                throw new ResponseStatusException(400, "Id should be empty");
            }else if (pollDTO.getCreatedBy() == null || pollDTO.getCreatedBy().trim().isEmpty()){
                throw new ResponseStatusException(400, "Invalid user");
            }else if ((pollDTO.getUpVotes() != null && pollDTO.getUpVotes() != 0) ||
                    (pollDTO.getDownVotes() != null && pollDTO.getDownVotes() != 0)) {
                throw new ResponseStatusException(400, "Votes count should be zero");
            }else if (pollDTO.getTitle() == null || pollDTO.getTitle().trim().isEmpty()){
                throw new ResponseStatusException(400, "Invalid title");
            }

            PollService pollService = ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.POLL);
            pollDTO = pollService.savePoll(pollDTO);

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_CREATED);
            jsonb.toJson(pollDTO, resp.getWriter());
        }catch (JsonbException t){
            throw new ResponseStatusException(400, "Invalid JSON", t);
        }

    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pollId = getPollId(req);

        /* Validate the content type */
        if (req.getContentType() == null || !req.getContentType().toLowerCase()
                .startsWith("application/json")){
            throw new ResponseStatusException(415, "Invalid content type");
        }
        try{
            /* Convert json -> PollDTO */
            Jsonb jsonb = JsonbBuilder.create();
            PollDTO pollDTO = jsonb.fromJson(req.getReader(), PollDTO.class);

            /* Validate pollDTO */
            if (pollDTO.getId() != null && pollDTO.getId() != pollId){
                throw new ResponseStatusException(400, "Id mismatched error");
            }else if (pollDTO.getCreatedBy() == null || pollDTO.getCreatedBy().trim().isEmpty()){
                throw new ResponseStatusException(400, "Invalid user");
            } else if (pollDTO.getUpVotes() == null || pollDTO.getDownVotes() == null) {
                throw new ResponseStatusException(400, "Invalid votes count");
            } else if (pollDTO.getUpVotes() < 0 || pollDTO.getDownVotes() < 0) {
                throw new ResponseStatusException(400, "Votes count can't be negative");
            }else if (pollDTO.getTitle() == null || pollDTO.getTitle().trim().isEmpty()){
                throw new ResponseStatusException(400, "Invalid title");
            }

            PollService pollService = ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.POLL);
            pollService.updatePoll(pollDTO);

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

        }catch (JsonbException t){
            throw new ResponseStatusException(400, "Invalid JSON", t);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pollId = getPollId(req);

        PollService pollService = ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.POLL);
        pollService.deletePoll(pollId);

        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

}
