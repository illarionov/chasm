(module
  (type $ft (func (result i32)))
  (type $st (struct (field i16)))
  (type $at (array i8))

  (table 10 anyref)

  (elem declare func $f)
  (func $f (result i32) (i32.const 9))

  (func (export "init") (param $x externref)
    (table.set (i32.const 0) (ref.null any))
    (table.set (i32.const 1) (ref.i31 (i32.const 7)))
    (table.set (i32.const 2) (struct.new $st (i32.const 6)))
    (table.set (i32.const 3) (array.new $at (i32.const 5) (i32.const 3)))
    (table.set (i32.const 4) (any.convert_extern (local.get $x)))
  )

  (func (export "br_on_null") (param $i i32) (result i32)
    (block $l
      (br_on_null $l (table.get (local.get $i)))
      (return (i32.const -1))
    )
    (i32.const 0)
  )
  (func (export "br_on_i31") (param $i i32) (result i32)
    (block $l (result (ref i31))
      (br_on_cast $l anyref (ref i31) (table.get (local.get $i)))
      (return (i32.const -1))
    )
    (i31.get_u)
  )
  (func (export "br_on_struct") (param $i i32) (result i32)
    (block $l (result (ref struct))
      (br_on_cast $l anyref (ref struct) (table.get (local.get $i)))
      (return (i32.const -1))
    )
    (block $l2 (param structref) (result (ref $st))
      (block $l3 (param structref) (result (ref $at))
        (br_on_cast $l2 structref (ref $st))
        (br_on_cast $l3 anyref (ref $at))
        (return (i32.const -2))
      )
      (return (array.get_u $at (i32.const 0)))
    )
    (struct.get_s $st 0)
  )
  (func (export "br_on_array") (param $i i32) (result i32)
    (block $l (result (ref array))
      (br_on_cast $l anyref (ref array) (table.get (local.get $i)))
      (return (i32.const -1))
    )
    (array.len)
  )

  (func (export "null-diff") (param $i i32) (result i32)
    (block $l (result (ref null struct))
      (block (result (ref any))
        (br_on_cast $l (ref null any) (ref null struct) (table.get (local.get $i)))
      )
      (return (i32.const 0))
    )
    (return (i32.const 1))
  )
)
