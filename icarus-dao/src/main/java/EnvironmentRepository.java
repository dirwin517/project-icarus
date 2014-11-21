
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.List;

import org.icarus.model.instances.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongodb.DB;
import com.mongodb.MongoClient;
public class EnvironmentRepository implements MongoRepository<Environment, String>{

	public Page<Environment> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	public <S extends Environment> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public Environment findOne(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public Iterable<Environment> findAll(Iterable<String> ids) {
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

	public void delete(Environment entity) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Iterable<? extends Environment> entities) {
		// TODO Auto-generated method stub
		
	}

	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	public <S extends Environment> List<S> save(Iterable<S> entites) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Environment> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Environment> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
