### OperationContext prototype
```bash
> Task :kkashin.dev.Main.main()
[AppLogger] created bean
[AppLogger] initialized
true
[OperationContext] bean created
[OperationContext] initialized
[OperationContext] bean created
[OperationContext] initialized
[OperationContext] bean created
[OperationContext] initialized
false
[AppLogger] destroyed
```

### OperationContext singleton
```bash
> Task :kkashin.dev.Main.main()
[AppLogger] created bean
[AppLogger] initialized
[OperationContext] bean created
[OperationContext] initialized
true
true
[OperationContext] destroyed
[AppLogger] destroyed
```

### Conclusion
`OperationContext` bean is creating at different time and destroying it handles differently