package lk.sajith.dep8.polling.service.custom.impl;

import lk.sajith.dep8.polling.dao.DAOFactory;
import lk.sajith.dep8.polling.dao.custom.PollDAO;
import lk.sajith.dep8.polling.dto.PollDTO;
import lk.sajith.dep8.polling.service.custom.PollService;
import lk.sajith.dep8.polling.service.util.EntityDTOTransformer;
import lk.sajith.dep8.polling.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class PollServiceImpl implements PollService {

    @Override
    public List<PollDTO> listAllPolls() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        PollDAO pollDAO = DAOFactory.getInstance().getDAO(em, DAOFactory.DAOType.POLL);
        try {
            return pollDAO.findAll().stream().map(EntityDTOTransformer::getPollDTO)
                    .collect(Collectors.toList());
        }finally{
            em.close();
        }
    }

    @Override
    public PollDTO getPoll(int id) {
        return null;
    }

    @Override
    public PollDTO savePoll(PollDTO dto) {
        return null;
    }

    @Override
    public void updatePoll(PollDTO dto) {

    }

    @Override
    public void deletePoll(int id) {

    }

}
