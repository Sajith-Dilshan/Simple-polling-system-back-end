package lk.sajith.dep8.polling.service.util;

import lk.sajith.dep8.polling.dto.PollDTO;
import lk.sajith.dep8.polling.entity.Poll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityDTOTransformerTest {

    @Test
    void getPollDTO() {

        // given
        Poll poll = new Poll(1, "This is a title", 10, 20, "Dulanga");
        // when
        PollDTO pollDTO = EntityDTOTransformer.getPollDTO(poll);
        // then
        assertEquals(poll.getId(), pollDTO.getId());
        assertEquals(poll.getCreatedBy(), pollDTO.getCreatedBy());
        assertEquals(poll.getUpVotes(), pollDTO.getUpVotes());
        assertEquals(poll.getDownVotes(), pollDTO.getDownVotes());
        assertEquals(poll.getTitle(), pollDTO.getTitle());

    }

    @Test
    void getPoll() {

        // given
        PollDTO pollDTO = new PollDTO(1, "This is a title", "Dulanga", 10, 5);
        // when
        Poll poll = EntityDTOTransformer.getPoll(pollDTO);
        // then
        assertEquals(poll.getId(), pollDTO.getId());
        assertEquals(poll.getCreatedBy(), pollDTO.getCreatedBy());
        assertEquals(poll.getUpVotes(), pollDTO.getUpVotes());
        assertEquals(poll.getDownVotes(), pollDTO.getDownVotes());
        assertEquals(poll.getTitle(), pollDTO.getTitle());

    }
}