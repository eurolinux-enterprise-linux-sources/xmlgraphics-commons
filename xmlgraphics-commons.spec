Name:           xmlgraphics-commons
Version:        1.5
Release:        3%{?dist}
Epoch:          0
Summary:        XML Graphics Commons

Group:          Development/Libraries
License:        ASL 2.0
URL:            http://xmlgraphics.apache.org/
Source0:        http://apache.skknet.net/xmlgraphics/commons/source/%{name}-%{version}-src.tar.gz

BuildArch:      noarch
BuildRequires:  java-devel >= 1:1.6.0
BuildRequires:  jpackage-utils
BuildRequires:  ant >= 0:1.6
BuildRequires:  ant-junit >= 0:1.6
BuildRequires:  junit
BuildRequires:  apache-commons-io >= 1.3.1
BuildRequires:  apache-commons-logging >= 1.0.4
Requires:       java
Requires:       jpackage-utils
Requires:       apache-commons-io >= 1.3.1
Requires:       apache-commons-logging >= 1.0.4

%description
Apache XML Graphics Commons is a library that consists of
several reusable components used by Apache Batik and
Apache FOP. Many of these components can easily be used
separately outside the domains of SVG and XSL-FO. You will
find components such as a PDF library, an RTF library,
Graphics2D implementations that let you generate PDF &
PostScript files, and much more.

%package        javadoc
Summary:        Javadoc for %{name}
Group:          Documentation
Requires:       jpackage-utils

%description    javadoc
%{summary}.


%prep
%setup -q %{name}-%{version}
rm -f `find . -name "*.jar"`

# create pom from template
sed "s:@version@:%{version}:g" %{name}-pom-template.pom \
    > %{name}.pom


%build
export CLASSPATH=$(build-classpath commons-logging)
export OPT_JAR_LIST="ant/ant-junit junit"
pushd lib
ln -sf $(build-classpath commons-io) .
popd
ant package javadocs

%install
install -d -m 755 $RPM_BUILD_ROOT%{_mavenpomdir}
install -Dpm 0644 build/%{name}-%{version}.jar $RPM_BUILD_ROOT%{_javadir}/%{name}.jar
install -pm 644 %{name}.pom $RPM_BUILD_ROOT/%{_mavenpomdir}/JPP-%{name}.pom
%add_maven_depmap JPP-%{name}.pom %{name}.jar

install -d -m 755 $RPM_BUILD_ROOT%{_javadocdir}/%{name}
cp -pr build/javadocs/* $RPM_BUILD_ROOT%{_javadocdir}/%{name}

%files
%doc LICENSE NOTICE README
%{_mavendepmapfragdir}/%{name}
%{_mavenpomdir}/JPP-%{name}.pom
%{_javadir}/*.jar

%files javadoc
%doc LICENSE NOTICE
%doc %{_javadocdir}/%{name}


%changelog
* Fri Dec 27 2013 Daniel Mach <dmach@redhat.com> - 01.5-3
- Mass rebuild 2013-12-27

* Fri Jun 28 2013 Mikolaj Izdebski <mizdebsk@redhat.com> - 0:1.5-2
- Rebuild to regenerate API documentation
- Resolves: CVE-2013-1571

* Tue Mar 19 2013 Mat Booth <fedora@matbooth.co.uk> - 0:1.5-1
- Update to 1.5, rhbz #895934
- Drop unneeded patch

* Fri Feb 15 2013 Fedora Release Engineering <rel-eng@lists.fedoraproject.org> - 0:1.4-7
- Rebuilt for https://fedoraproject.org/wiki/Fedora_19_Mass_Rebuild

* Sun Jul 22 2012 Fedora Release Engineering <rel-eng@lists.fedoraproject.org> - 0:1.4-6
- Rebuilt for https://fedoraproject.org/wiki/Fedora_18_Mass_Rebuild

* Tue Mar 06 2012 Deepak Bhole <dbhole@redhat.com> 1.4-5
- Added dist to the release tag

* Thu Mar 01 2012 Jiri Vanek <jvanek@redhat.com> - 0:1.4-5
- Resolves: rhbz#796341
- Added xmlgraphics-commons-java-7-fix.patch to fix build with Java 7

* Sat Jan 14 2012 Fedora Release Engineering <rel-eng@lists.fedoraproject.org> - 0:1.4-4
- Rebuilt for https://fedoraproject.org/wiki/Fedora_17_Mass_Rebuild

* Tue May  3 2011 Stanislav Ochotnicky <sochotnicky@redhat.com> - 0:1.4-3
- Install maven metadata
- Versionless jars & javadocs
- Fixes according to new guidelines

* Mon Feb 07 2011 Fedora Release Engineering <rel-eng@lists.fedoraproject.org> - 0:1.4-2
- Rebuilt for https://fedoraproject.org/wiki/Fedora_15_Mass_Rebuild

* Fri Oct 1 2010 Alexander Kurtakov <akurtako@redhat.com> 0:1.4-1
- Updte to 1.4.

* Sat Jan 9 2010 Alexander Kurtakov <akurtako@redhat.com> 0:1.3.1-1
- Update to 1.3.1.
- Fix Source0 url.

* Mon Jul 27 2009 Fedora Release Engineering <rel-eng@lists.fedoraproject.org> - 0:1.3-3
- Rebuilt for https://fedoraproject.org/wiki/Fedora_12_Mass_Rebuild

* Thu Feb 26 2009 Fedora Release Engineering <rel-eng@lists.fedoraproject.org> - 0:1.3-2
- Rebuilt for https://fedoraproject.org/wiki/Fedora_11_Mass_Rebuild

* Wed Apr 02 2008 Lillian Angel <langel at redhat.com> - 0:1.3-1
- Added java-1.6.0-openjdk-devel as build requirement.

* Mon Mar 31 2008 Lillian Angel <langel at redhat.com> - 0:1.3-1
- Updated sources to 1.3.

* Fri Nov 23 2007 Lillian Angel <langel at redhat.com> - 0:1.2-1
- Added epoch.

* Fri Nov 23 2007 Lillian Angel <langel at redhat.com> - 0:1.2-1
- Added missing BuildRoot line.

* Fri Nov 23 2007 Lillian Angel <langel at redhat.com> - 0:1.2-1
- Fixed install section.

* Fri Nov 23 2007 Lillian Angel <langel at redhat.com> - 0:1.2-1
- Fixed rpmlint errors.

* Tue Sep 18 2007 Joshua Sumali <jsumali at redhat.com> - 0:1.2-1jpp
- Update to 1.2

* Tue May 23 2006 Ralph Apel <r.apel at r-apel.de> - 0:1.0-1jpp
- First JPP-1.7 release
