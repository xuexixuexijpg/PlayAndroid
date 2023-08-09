# 项目学习参考
1.https://github.com/FPhoenixCorneaE/JetpackMvvm

2.https://github.com/android/nowinandroid

## 生成compose报告 Compose compiler metrics
```
./gradlew assembleRelease -PenableComposeCompilerMetrics=true -PenableComposeCompilerReports=true
```
The reports files will be added to build/compose-reports in each module. The metrics files will be
added to build/compose-metrics in each module.

For more information on Compose compiler metrics,
see [this blog post](https://medium.com/androiddevelopers/jetpack-compose-stability-explained-79c10db270c8).
see [github](https://github.com/androidx/androidx/blob/androidx-main/compose/compiler/design/compiler-metrics.md)
see [jvejin](https://juejin.cn/post/7110208846051672095)