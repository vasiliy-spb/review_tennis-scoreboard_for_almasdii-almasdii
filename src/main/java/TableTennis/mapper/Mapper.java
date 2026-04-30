package TableTennis.mapper;

public interface Mapper<T,K>{
    T mapFrom(K k);
}
