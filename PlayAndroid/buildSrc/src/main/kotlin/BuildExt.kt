import org.gradle.api.artifacts.dsl.DependencyHandler

//依赖implementation
fun DependencyHandler.addImplementation(list: List<String>) {
    for (version in list){
        add("implementation", version)
    }
}

//依赖kapt
fun DependencyHandler.addKapt(list: List<String>) {
    for (version in list){
        add("kapt", version)
    }
}