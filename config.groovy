environments {
    dev {
        neo4jServerName = 'http://localhost:7474'
        dataDir= 'src/main/resources'
        webappRoot= 'src/main/webapp'        
    }
    
    docker {
        neo4jServerName = 'http://neo4j:7474'
        dataDir= '/acuo-neo4j/data'
        webappRoot= '/acuo-neo4j/webapp'       
    }
}