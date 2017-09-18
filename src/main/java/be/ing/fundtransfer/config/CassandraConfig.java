package be.ing.fundtransfer.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.config.CassandraCqlClusterFactoryBean;
import org.springframework.cassandra.config.DataCenterReplication;
import org.springframework.cassandra.core.keyspace.CreateKeyspaceSpecification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories("be.ing.fundtransfer.repository")
public class CassandraConfig extends AbstractCassandraConfiguration{
	
    private static final String KEYSPACE = "FundTransferApp";
    private static final String USERNAME = "cassandra";
    private static final String PASSWORD = "cassandra";
    private static final String NODES = "127.0.0.1"; // comma seperated nodes
    
    @Autowired
    private Environment environment;

    @Bean
    @Override
    public CassandraCqlClusterFactoryBean cluster() {
        CassandraCqlClusterFactoryBean bean = new CassandraCqlClusterFactoryBean();
        bean.setKeyspaceCreations(getKeyspaceCreations());
        bean.setContactPoints(NODES);
        bean.setUsername(USERNAME);
        bean.setPassword(PASSWORD);
        return bean;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    protected String getKeyspaceName() {
        return KEYSPACE;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"be.ing.fundtransfer.data"};
    }


    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        List<CreateKeyspaceSpecification> createKeyspaceSpecifications = new ArrayList<>();
        createKeyspaceSpecifications.add(getKeySpaceSpecification());
        return createKeyspaceSpecifications;
    }

    // Below method creates "my_keyspace" if it doesnt exist.
    private CreateKeyspaceSpecification getKeySpaceSpecification() {
        CreateKeyspaceSpecification pandaCoopKeyspace = new CreateKeyspaceSpecification();
        DataCenterReplication dcr = new DataCenterReplication("datacenter1", 3L);
        pandaCoopKeyspace.name(KEYSPACE);
        pandaCoopKeyspace.ifNotExists(true).createKeyspace().withNetworkReplication(dcr);
        return pandaCoopKeyspace;
    }
}

