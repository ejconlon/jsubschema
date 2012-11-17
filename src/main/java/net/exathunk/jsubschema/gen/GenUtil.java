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

    public static class GetAccessorGen implements AccessorGen {
        @Override
        public MethodRep genAccessor(final FieldRep fieldRep) {
            final MethodRep method = new MethodRep();
            method.classAnnotations.add(new AnnotationRep("@Override"));
            method.interfaceAnnotations.add(new AnnotationRep("@JsonProperty(\""+Util.unconvert(fieldRep.name)+"\")"));
            method.name = "get"+ Util.capitalize(fieldRep.name);
            method.returns = fieldRep.className;
            method.body = new Stringable() {
                @Override
                public void makeString(final Stringer sb) {
                    sb.append("return ").append(fieldRep.name).append(";");
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
            method.classAnnotations.add(new AnnotationRep("@Override"));
            method.name = "has"+ Util.capitalize(fieldRep.name);
            method.returns = "boolean";
            method.body = new Stringable() {
                @Override
                public void makeString(final Stringer sb) {
                    sb.append("return null != ").append(fieldRep.name).append(";");
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
            method.classAnnotations.add(new AnnotationRep("@Override"));
            method.interfaceAnnotations.add(new AnnotationRep("@JsonProperty(\""+Util.unconvert(fieldRep.name)+"\")"));
            if (fieldRep.className.endsWith("Like") || fieldRep.className.endsWith("Like>")) {
                if (fieldRep.className.startsWith("Map<") || fieldRep.className.startsWith("List<")) {
                    final int space = fieldRep.className.lastIndexOf(" ");
                    final String className = fieldRep.className.substring(space+1, fieldRep.className.length()-"Like>".length());
                    method.interfaceAnnotations.add(new AnnotationRep("@JsonDeserialize(contentAs = "+className+".class)"));
                } else {
                    final String className = fieldRep.className.substring(0, fieldRep.className.length()-"Like".length());
                    method.interfaceAnnotations.add(new AnnotationRep("@JsonDeserialize(as = "+className+".class)"));
                }
            }
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
            method.classAnnotations.add(new AnnotationRep("@Override"));
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
            method.classAnnotations.add(new AnnotationRep("@Override"));
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
                    sb.append("if (o instanceof ").append(classRep.name).append("Like) {");
                    sb.end();
                    sb.indent().append(classRep.name).append("Like other = (").append(classRep.name).append("Like) o;");
                    sb.end();
                    for (FieldRep field : classRep.fields) {
                        sb.indent().append("if (" + field.name + " == null) { if (other.has" + Util.capitalize(field.name) + "()) { return false; } }");
                        sb.end();
                        sb.indent().append("else if (!" + field.name + ".equals(other.get" + Util.capitalize(field.name) + "())) { return false; }");
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
            method.classAnnotations.add(new AnnotationRep("@Override"));
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


    public static class DiffMethodGen implements MethodGen {
        @Override
        public MethodRep genMethod(final ClassRep classRep) {
            final MethodRep method = new MethodRep();
            method.name = "diff";
            method.returns = "Set<String>";
            FieldRep otherField = new FieldRep();
            otherField.name = "other";
            otherField.className = classRep.name+"Like";
            method.parameters.add(otherField);

            method.body = new Stringable() {
                @Override
                public void makeString(final Stringer sb) {
                    sb.indent().append("Set<String> s = new TreeSet<String>();");
                    sb.end();
                    for (FieldRep field : classRep.fields) {
                        sb.indent().append("if (" + field.name + " == null) { if (other == null || other.has" + Util.capitalize(field.name) + "()) { s.add(\""+field.name+"\"); } }");
                        sb.end();
                        sb.indent().append("else if (!" + field.name + ".equals(other.get" + Util.capitalize(field.name) + "())) { s.add(\""+field.name+"\"); }");
                        sb.end();
                    }
                    sb.indent().append("return s;");
                    sb.end();
                }
            };

            // HACK
            classRep.imports.add("java.util.Set");
            classRep.imports.add("java.util.TreeSet");

            return method;
        }
    }
}
