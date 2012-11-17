package net.exathunk.jsubschema.gen;

import net.exathunk.jsubschema.base.Util;

/**
 * charolastra 11/16/12 2:41 PM
 */
public class GenUtil {

    public static interface MethodGen {
        MethodRep genMethod(ClassRep classRep);
    }

    public static interface AccessorGen {
        MethodRep genAccessor(FieldRep fieldRep);
    }

    public static class GetterAccessorGen implements AccessorGen {
        @Override
        public MethodRep genAccessor(final FieldRep fieldRep) {
            final MethodRep method = new MethodRep();
            method.annotations.add(new AnnotationRep("@Override"));
            method.name = "get"+ Util.capitalize(fieldRep.name);
            method.returns = fieldRep.className;
            method.body = new Stringable() {
                @Override
                public void makeString(final Stringer sb) {
                    sb.append("return ").append(fieldRep.name);
                    sb.end();
                }
            };
            return method;
        }
    }

    public static class HasAccessorGen implements AccessorGen {
        @Override
        public MethodRep genAccessor(final FieldRep fieldRep) {
            final MethodRep method = new MethodRep();
            method.annotations.add(new AnnotationRep("@Override"));
            method.name = "has"+ Util.capitalize(fieldRep.name);
            method.returns = "boolean";
            method.body = new Stringable() {
                @Override
                public void makeString(final Stringer sb) {
                    sb.append("return null != ").append(fieldRep.name);
                    sb.end();
                }
            };
            return method;
        }
    }

    public static class SetAccessorGen implements AccessorGen {
        @Override
        public MethodRep genAccessor(final FieldRep fieldRep) {
            final MethodRep method = new MethodRep();
            method.annotations.add(new AnnotationRep("@Override"));
            method.name = "set"+ Util.capitalize(fieldRep.name);
            method.returns = "void";
            FieldRep otherField = new FieldRep();
            otherField.name = fieldRep.name;
            otherField.className = fieldRep.className;
            method.parameters.add(otherField);
            method.body = new Stringable() {
                @Override
                public void makeString(final Stringer sb) {
                    sb.append("this.").append(fieldRep.name).append(" = ").append(fieldRep.name).append(";");
                    sb.end();
                }
            };
            return method;
        }
    }

    public static class ToStringMethodGen implements MethodGen {
        @Override
        public MethodRep genMethod(final ClassRep classRep) {
            final MethodRep method = new MethodRep();
            method.annotations.add(new AnnotationRep("@Override"));
            method.name = "toString";
            method.returns = "String";

            method.body = new Stringable() {
                @Override
                public void makeString(final Stringer sb) {
                    sb.append("StringBuilder sb = new StringBuilder(\"").append(classRep.name).append("{ \");");
                    sb.end();
                    for (FieldRep field : classRep.fields) {
                        sb.append("if (").append(field.name).append(" != null) sb.append(\"");
                        sb.cont().append(field.name).append("='\")").append(".append(").append(field.name).append(").append(\"', \");");
                        sb.end();
                    }
                    sb.append("return sb.append(\"}\").toString();");
                    sb.end();
                }
            };

            return method;
        }
    }

    public static class EqualsMethodGen implements MethodGen {
        @Override
        public MethodRep genMethod(final ClassRep classRep) {
            final MethodRep method = new MethodRep();
            method.annotations.add(new AnnotationRep("@Override"));
            method.name = "equals";
            method.returns = "boolean";
            FieldRep otherField = new FieldRep();
            otherField.name = "o";
            otherField.className = "Object";
            method.parameters.add(otherField);

            method.body = new Stringable() {
                @Override
                public void makeString(final Stringer sb) {
                    sb.append("if (this == o) return true;");
                    sb.end();
                    sb.append("if (o instanceof ").append(classRep.name).append(") {");
                    sb.end();
                    sb.indent().append(classRep.name).append(" other = (").append(classRep.name).append(") o;");
                    sb.end();
                    for (FieldRep field : classRep.fields) {
                        sb.indent().append("if (" + field.name + " == null) { if (other." + field.name + " != null) return false; }");
                        sb.end();
                        sb.indent().append("else if (!" + field.name + ".equals(other." + field.name + ")) { return false; }");
                        sb.end();
                    }
                    sb.indent().append("return true;");
                    sb.end();
                    sb.append("} else {");
                    sb.end();
                    sb.indent().append("return false;");
                    sb.end();
                    sb.append("}");
                    sb.end();
                }
            };

            return method;
        }
    }

    public static class HashCodeMethodGen implements MethodGen {
        @Override
        public MethodRep genMethod(final ClassRep classRep) {
            final MethodRep method = new MethodRep();
            method.annotations.add(new AnnotationRep("@Override"));
            method.name = "hashCode";
            method.returns = "int";

            method.body = new Stringable() {
                @Override
                public void makeString(final Stringer sb) {
                    sb.append("int result = 0;");
                    sb.end();
                    for (FieldRep field : classRep.fields) {
                        sb.append("result = 31 * result + ("+field.name+" == null ? 0 : ").append(field.name).append(".hashCode());");
                        sb.end();
                    }
                    sb.append("return result;");
                    sb.end();
                }
            };

            return method;
        }
    }

}
