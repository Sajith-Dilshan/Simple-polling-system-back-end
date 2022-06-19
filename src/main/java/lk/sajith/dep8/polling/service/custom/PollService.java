package lk.sajith.dep8.polling.service.custom;

import lk.sajith.dep8.polling.dto.PollDTO;
import lk.sajith.dep8.polling.service.SuperService;

import java.util.List;

public interface PollService extends SuperService {

    List<PollDTO> listAllPolls();

    PollDTO getPoll(int id);

    PollDTO savePoll(PollDTO dto);

    void updatePoll(PollDTO dto);

    void deletePoll(int id);

}
