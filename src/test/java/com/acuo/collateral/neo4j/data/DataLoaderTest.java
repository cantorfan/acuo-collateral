package com.acuo.collateral.neo4j.data;

import static org.mockito.Matchers.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.neo4j.ogm.session.Session;

import com.acuo.collateral.persist.CypherFileSpliter;
import com.acuo.collateral.persist.DataLoader;
import com.acuo.collateral.persist.SessionDataLoader;

@Ignore
public class DataLoaderTest {

	@Rule
	public ExpectedException	expectedException	= ExpectedException.none();
	
	@Mock
	Session session;
		
	DataLoader loader;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		loader = new SessionDataLoader(session);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testLoadDataWithEmptyQuery() {
		loader.loadData("");
		
		verify(session, times(0)).query(anyString(), anyMap());
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testLoadDataWithNullQuery() {
		loader.loadData(null);
		
		verify(session, times(0)).query(anyString(), anyMap());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testLoadDataWithDummyQuery() {
		loader.loadData("dummy");
		
		verify(session, times(1)).query("dummy", anyMap());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteAll() {
//		when(spliter
//				.splitByDefaultDelimiter(SessionDataLoader.DELETE_ALL_CQL))
//				.thenReturn(Arrays.asList("deleteAllQuery"));
		
		loader.purgeDatabase();
		
		verify(session, times(1)).query("deleteAllQuery", anyMap());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCreateConstraints() {
//		when(spliter
//				.splitByDefaultDelimiter(SessionDataLoader.CONSTRAINTS_CQL))
//				.thenReturn(Arrays.asList("createConstraintsQuery"));
		
		loader.createConstraints();
		
		verify(session, times(1)).query("createConstraintsQuery", anyMap());
	}

}
