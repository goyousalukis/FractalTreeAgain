#!/usr/bin/env python
import sys
sys.path.append('/usr/share/inkscape/extensions') # or another path, as necessary
import inkex
from simplestyle import *
class HelloWorldEffect(inkex.Effect):
    def __init__(self):
        inkex.Effect.__init__(self)
        self.OptionParser.add_option('-w', '--what', action = 'store',
          type = 'string', dest = 'what', default = 'World',
          help = 'What would you like to greet?')
    def effect(self):
        what = self.options.what
        svg = self.document.getroot()
        # or alternatively
        # svg = self.document.xpath('//svg:svg',namespaces=inkex.NSS)[0]

        # Again, there are two ways to get the attibutes:
        width  = self.unittouu(svg.get('width'))
        height = self.unittouu(svg.attrib['height'])
        layer = inkex.etree.SubElement(svg, 'g')
        layer.set(inkex.addNS('label', 'inkscape'), 'Hello %s Layer' % (what))
        layer.set(inkex.addNS('groupmode', 'inkscape'), 'layer')
        text = inkex.etree.Element(inkex.addNS('text','svg'))
        text.text = 'Hello %s!' % (what)
        text.set('x', str(width / 2))
        text.set('y', str(height / 2))
        style = {'text-align' : 'center', 'text-anchor' : 'middle'}
        text.set('style', formatStyle(style))
  layer.append(text)
effect = HelloWorldEffect()
effect.affect()
