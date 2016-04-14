package com.acuo.collateral.neo4j.service;

import org.junit.Ignore;

@SuppressWarnings("unchecked")
@Ignore
public class CollateralServiceTest {

//	CollateralService movieService;
//    private GraphDatabaseService db;
//
//    @Before
//    public void setUp() throws Exception {
//        db = new TestGraphDatabaseFactory().newImpermanentDatabase();       
//        createData(db);
//        this.movieService = new CollateralService(db);
//    }
//
//    private void createData(GraphDatabaseService databaseService) {
//        String query = "CREATE (:Movie {title:'The Matrix', released: 1999, tagline: 'The one and only'})" +
//                " <-[:ACTED_IN {roles:['Neo']}]-" +
//                " (:Person {name:'Keanu Reeves',born:1964})";
//
//        databaseService.execute(query).resultAsString();
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        db.shutdown();
//    }
//
//    @Test
//    public void testFindMovie() throws Exception {
//        Map movie = movieService.findAsset("The Matrix");
//        assertEquals("The Matrix",movie.get("title"));
//        List<Map> cast = (List<Map>) movie.get("cast");
//        assertEquals(1,cast.size());
//        Map entry = cast.get(0);
//        assertEquals("Keanu Reeves", entry.get("name"));
//        assertEquals("acted",entry.get("job"));
//        assertArrayEquals(new String[] {"Neo"}, (String[])entry.get("role"));
//    }
//
//    @Test
//    public void testSearch() throws Exception {
//        Iterator<Map<String, Object>> result = movieService.search("matr").iterator();
//        Map<String, Object> movie = (Map<String, Object>) result.next().get("movie");
//        System.out.println("movie = " + movie);
//        assertEquals("The Matrix", movie.get("title"));
//        assertEquals(1999L, movie.get("released"));
//        assertEquals("The one and only", movie.get("tagline"));
//    }
//
//    @Test
//    public void testGraph() throws Exception {
//        Map<String, Object> graph = movieService.graph(100);
//        System.out.println("graph = " + graph);
//        List<Map<String,Object>> nodes = (List<Map<String, Object>>) graph.get("nodes");
//        assertEquals(asList(map("label", "movie", "title", "The Matrix"), map("label", "actor", "title", "Keanu Reeves")), nodes);
//        List<Map<String,Object>> links = (List<Map<String, Object>>) graph.get("links");
//        assertEquals(asList(map("source",1,"target",0)),links);
//    }
}
