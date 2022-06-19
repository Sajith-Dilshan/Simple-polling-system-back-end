package lk.sajith.dep8.polling.service.util;

import lk.sajith.dep8.polling.dto.PollDTO;
import lk.sajith.dep8.polling.entity.Poll;
import org.modelmapper.ModelMapper;

public class EntityDTOTransformer {


    public static PollDTO getPollDTO(Poll pollEntity){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(pollEntity, PollDTO.class);
    }

    public static Poll getPoll(PollDTO pollDTO){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(pollDTO, Poll.class);
    }

}
