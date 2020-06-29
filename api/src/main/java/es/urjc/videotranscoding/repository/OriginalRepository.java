package es.urjc.videotranscoding.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import es.urjc.videotranscoding.entities.Original;

@Repository
public interface OriginalRepository extends PagingAndSortingRepository<Original, Integer> {
	Page<Original> findAllByUser(Pageable page, String user);

	Page<Original> findAll(Pageable pageable);

}
