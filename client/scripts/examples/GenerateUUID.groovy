import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

def logger = LoggerFactory.getLogger("GenerateUUID");

[1..10]*.each {
	println UUID.randomUUID()
}
