
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.List;

import org.icarus.model.configs.StackConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongodb.DB;
import com.mongodb.MongoClient;
public class StackRepository implements MongoRepository<StackConfig, String>{

	public Page<StackConfig> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	public <S extends StackConfig> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public StackConfig findOne(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public Iterable<StackConfig> findAll(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	public void delete(StackConfig entity) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Iterable<? extends StackConfig> entities) {
		// TODO Auto-generated method stub
		
	}

	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	public <S extends StackConfig> List<S> save(Iterable<S> entites) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<StackConfig> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<StackConfig> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
